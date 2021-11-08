package com.example.hairsalonrest.service;

import com.example.hairsalonrest.dto.userdtos.UserAuthDto;
import com.example.hairsalonrest.dto.userdtos.UserAuthResponseDto;
import com.example.hairsalonrest.dto.userdtos.UserResetPasswordDto;
import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User save(User user);

    Optional<User> findUserById(int id);

    UserAuthResponseDto findUserByEmail(UserAuthDto userAuthDto);

    Optional<User> findUserByEmail(String email);

    User editUser(int id, User user);

    void deleteUserById(int id);

    User verifyEmail(String activeCode, CurrentUser currentUser);

    User resetPassword(User user, UserResetPasswordDto userReset);
    User sendToken(String email);
    void createAdmin();
}
