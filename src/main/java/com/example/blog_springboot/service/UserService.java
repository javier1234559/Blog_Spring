package com.example.blog_springboot.service;


import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.dto.UserLoginDTO;
import com.example.blog_springboot.dto.UserRegisterDTO;
import com.example.blog_springboot.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userdto);
    User createUserRegister(UserRegisterDTO userRegisterDTO);
    User updateUser(int id, User user);
    String deleteUser(int id);
    User getUserById(int id);
    User getUserByEmailAndPassword(UserLoginDTO userLoginDTO);
    List<User> getAllUsers();
}
