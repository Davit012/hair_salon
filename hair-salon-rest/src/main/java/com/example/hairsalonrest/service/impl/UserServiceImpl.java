package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.dto.userdtos.UserAuthDto;
import com.example.hairsalonrest.dto.userdtos.UserAuthResponseDto;
import com.example.hairsalonrest.dto.userdtos.UserDto;
import com.example.hairsalonrest.dto.userdtos.UserResetPasswordDto;
import com.example.hairsalonrest.repository.UserRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.service.EmailService;
import com.example.hairsalonrest.service.UserService;
import com.example.hairsalonrest.util.JwtTokenUtil;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        for (User fromDb : findAll()) {
            if (fromDb.getEmail().equals(user.getEmail())) {
                return null;
            }
        }
        user.setActiveCode(UUID.randomUUID());
        user.setUserType(UserType.valueOf("CLIENT"));
        userRepository.save(user);
        emailService.sendMessage(user.getEmail(), "Welcome, please verify your email", "Welcome dear " + user.getName()
                + " to our hair salon your activated code is " + user.getActiveCode());
        return user;
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserAuthResponseDto findUserByEmail(UserAuthDto userAuthDto) {
        return UserAuthResponseDto.builder()
                .token(jwtTokenUtil.generateToken(userAuthDto.getEmail()))
                .userDto(mapper.map(userAuthDto, UserDto.class))
                .build();
    }

    @Override
    public User editUser(int id, User user) {
        final Optional<User> byId = userRepository.findById(id);
        user.setId(id);
        byId.ifPresent(value -> mapper.map(user, value));
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User verifyEmail(String activeCode, CurrentUser currentUser) {
        Optional<User> userByEmail = findUserByEmail(currentUser.getUser().getEmail());
        if (userByEmail.isEmpty()) {
            return null;
        }
        User user = userByEmail.get();
        if (user.getActiveCode().equals(UUID.fromString(activeCode))) {
            user.setEmailVerified(true);
            user.setActiveCode(null);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User resetPassword(User user, UserResetPasswordDto userReset) {
        if (passwordEncoder.matches(userReset.getPassword(), user.getPassword()) &&
                user.getActiveCode().equals(UUID.fromString((userReset.getToken())))) {
            String newPassword = passwordEncoder.encode(userReset.getNewPassword());
            user.setActiveCode(null);
            user.setPassword(newPassword);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User sendToken(String email) {
        Optional<User> userByEmail = findUserByEmail(email);
        if (userByEmail.isEmpty()) {
            return null;
        }
        User user = userByEmail.get();
        if (user.isEmailVerified()) {
            UUID token = UUID.randomUUID();
            emailService.sendMessage(user.getEmail(), "Reset your Password",
                    "Dear " + user.getName() + " your token is " + token);
            user.setActiveCode(token);
            userRepository.save(user);
            return userRepository.save(user);
        }
        return null;
    }
    public void createAdmin() {
        if (userRepository.findByEmail("admin").isEmpty()) {

            userRepository.save(User.builder()
                    .email("admin")
                    .surname("admin")
                    .name("admin")
                    .password(passwordEncoder.encode("admin"))
                    .userType(UserType.valueOf("ADMIN"))
                    .isEmailVerified(true)
                    .build());
        }
    }
}