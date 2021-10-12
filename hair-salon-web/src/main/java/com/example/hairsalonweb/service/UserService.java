package com.example.hairsalonweb.service;

import com.hairsaloncommon.model.User;

import java.util.List;

public interface UserService {

    User add(User user);
    List<User> getAllUsers();
    void deleteUser(int id);
    User updateUser(int id, User user);
    User getUserById(int id);




}
