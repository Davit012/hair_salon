package com.hairsaloncommon.service.impl;

import com.hairsaloncommon.dto.userdtos.UserAuthDto;
import com.hairsaloncommon.dto.userdtos.UserAuthResponseDto;
import com.hairsaloncommon.dto.userdtos.UserDto;
import com.hairsaloncommon.dto.userdtos.UserResetPasswordDto;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import com.hairsaloncommon.repository.UserRepository;
import com.hairsaloncommon.service.EmailService;
import com.hairsaloncommon.service.UserService;
import com.hairsaloncommon.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Lazy))
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
    public User verifyEmail(String activeCode,User currentUser) {
        Optional<User> userByEmail = findUserByEmail(currentUser.getEmail());
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
}