package com.example.blog_springboot.service;


import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(int id, User user);
    void deleteUser(int id);
    User getUserById(int id);
//    User getUserByEmail(String email);
    List<User> getAllUsers();
}
