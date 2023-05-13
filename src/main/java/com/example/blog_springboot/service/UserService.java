package com.example.blog_springboot.service;


import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.dto.UserLoginDTO;
import com.example.blog_springboot.dto.UserRegisterDTO;
import com.example.blog_springboot.model.User;

import java.util.List;

public interface UserService {

    User createUserRegister(UserRegisterDTO userRegisterDTO);
    UserDTO updateUser(int id, UserDTO userDTO);
    String deleteUser(int id);
    User getUserById(int id);
    UserDTO getUserByEmail(String email);
    User getUserByEmailAndPassword(UserLoginDTO userLoginDTO);
    List<User> getAllUsers();
}
