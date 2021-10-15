package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.dto.userdtos.UserAuthDto;
import com.example.hairsalonrest.dto.userdtos.UserAuthResponseDto;
import com.example.hairsalonrest.dto.userdtos.UserDto;
import com.example.hairsalonrest.repository.UserRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.service.UserService;
import com.example.hairsalonrest.util.JwtTokenUtil;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        for (User fromDb : findAll()) {
            if(fromDb.getEmail().equals(user.getEmail())){
                return null;
            }
        }
        user.setActiveCode(UUID.randomUUID());
        user.setUserType(UserType.valueOf("CLIENT"));
         userRepository.save(user);
        emailService.sendMessage(user.getEmail(),"Welcome, please verify your email","Welcome dear " + user.getName()
                + " to our hair salon your activated code is " + user.getActiveCode());
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserAuthResponseDto findByEmail(UserAuthDto userAuthDto) {
            return UserAuthResponseDto.builder()
                    .token(jwtTokenUtil.generateToken(userAuthDto.getEmail()))
                    .userDto(mapper.map(userAuthDto, UserDto.class))
                    .build();
    }

    @Override
    public User editUser(int id, User user) {
        User byId = userRepository.findById(id).get();
        user.setId(id);
        mapper.map(user, byId);
        return userRepository.save(user);
    }
    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User verifyEmail(String activeCode, CurrentUser currentUser) {
        System.out.println(currentUser.getUser().getActiveCode());
        if (currentUser.getUser().getActiveCode().equals(UUID.fromString(activeCode))){
            currentUser.getUser().setEmailVerified(true);
            currentUser.getUser().setActiveCode(null);
            return save(currentUser.getUser());
        }
        return null;
    }
}