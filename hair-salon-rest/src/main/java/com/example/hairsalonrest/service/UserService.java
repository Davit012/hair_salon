package com.example.hairsalonrest.service;

import com.example.hairsalonrest.dto.userdtos.UserAuthDto;
import com.example.hairsalonrest.dto.userdtos.UserAuthResponseDto;
import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User save(User user);

    Optional<User> findById(int id);

    UserAuthResponseDto findByEmail(UserAuthDto userAuthDto);

    Optional<User> findByEmail(String email);

    User editUser(int id, User user);

    void deleteById(int id);

    User verifyEmail(String activeCode, CurrentUser currentUser);
}
