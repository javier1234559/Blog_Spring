package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.UserRepository;
import com.example.blog_springboot.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
