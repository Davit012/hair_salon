package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.dto.userdtos.UserAuthDto;
import com.example.hairsalonrest.dto.userdtos.UserAuthResponseDto;
import com.example.hairsalonrest.dto.userdtos.UserDto;
import com.example.hairsalonrest.repository.UserRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.service.EmailService;
import com.example.hairsalonrest.service.UserService;
import com.example.hairsalonrest.util.JwtTokenUtil;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        if (currentUser.getUser().getActiveCode().equals(UUID.fromString(activeCode))) {
            currentUser.getUser().setEmailVerified(true);
            currentUser.getUser().setActiveCode(null);
            return save(currentUser.getUser());
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