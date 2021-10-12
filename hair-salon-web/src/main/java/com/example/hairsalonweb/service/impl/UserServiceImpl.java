package com.example.hairsalonweb.service.impl;

import com.example.hairsalonweb.repository.UserRepository;
import com.example.hairsalonweb.service.UserService;
import com.hairsaloncommon.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(int id, User user) {
        User userById = userRepository.findById(id).get();
        userById.setId(user.getId());
        userById.setName(user.getName());
        userById.setSurname(user.getSurname());
        userById.setGender(user.getGender());
        userById.setEmail(user.getEmail());
        userById.setPassword(user.getPassword());
        userById.setUserType(user.getUserType());
        return userRepository.save(userById);
    }
}
