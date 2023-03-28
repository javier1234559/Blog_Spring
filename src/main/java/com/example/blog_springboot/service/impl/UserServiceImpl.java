package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.UserRepository;
import com.example.blog_springboot.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        User updatedUser = userRepository.findById(id).orElse(null);
        if (updatedUser != null) {
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPass(user.getPass());
            updatedUser.setPhone(user.getPhone());
            updatedUser.setDescription(user.getDescription());
            updatedUser.setImage(user.getImage());
            updatedUser.setStatus(user.getStatus());
            updatedUser.setRole(user.getRole());
            userRepository.save(updatedUser);
        }
        return updatedUser;
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

//    @Override
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email).orElse(null);
//    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
