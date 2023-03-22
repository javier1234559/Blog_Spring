package com.example.blog_springboot.service;


import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {



    public List<User> findAllUsers() ;
}
