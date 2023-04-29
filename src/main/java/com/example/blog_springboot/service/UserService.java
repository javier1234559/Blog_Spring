package com.example.blog_springboot.service;


import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.model.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userdto);
    User updateUser(int id, User user);
    String deleteUser(int id);
    User getUserById(int id);
    List<User> getAllUsers();
}
