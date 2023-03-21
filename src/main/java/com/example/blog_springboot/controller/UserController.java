package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String test(Model model) {
        List<User> users = userService.findAllUsers();
        StringBuilder usersStr = new StringBuilder();
        for (User user : users) {
            usersStr.append(user.toString()).append("\n");
        }
        model.addAttribute("users", usersStr.toString());
        return "test";
    }
    @GetMapping("/users")
    public String findAllUsers(Model model) {
        List<User> users = userService.findAllUsers();
        StringBuilder usersStr = new StringBuilder();
        for (User user : users) {
            usersStr.append(user.toString()).append("\n");
        }
        model.addAttribute("users", usersStr.toString());
        return "test";
    }


}
