package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.exception.ResourceExistException;
import com.example.blog_springboot.exception.ResourceNotFoundException;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.UserRepository;
import com.example.blog_springboot.service.UserService;


import com.example.blog_springboot.ultilies.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO createUser(UserDTO userdto) {
        try {
//            if (userRepository.existsByMail(request.getMail())) {
//                throw new ResourceExistException(AppConstant.USER_EXIST);
//            }
            User user = new User();
            userRepository.save(user);
            return userdto ;
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error creating user", ex);
        }
    }

    @Override
    public User updateUser(int id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPass(user.getPass());
            updatedUser.setPhone(user.getPhone());
            updatedUser.setDescription(user.getDescription());
            updatedUser.setImage(user.getImage());
            updatedUser.setStatus(user.getStatus());
            updatedUser.setRole(user.getRole());
            userRepository.save(updatedUser);
            return updatedUser;
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public User getUserById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    public String deleteUser(int id) {
        Optional<User> post = userRepository.findById(id);
        if (post.isPresent()) {
            userRepository.deleteById(id);
            return "User" + Constant.DELETE_SUCCESSFULLY;
        } else {
            throw new ResourceExistException("User " + Constant.DELETE_FAILED);
        }
    }

}
