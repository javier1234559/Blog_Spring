package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.dto.UserLoginDTO;
import com.example.blog_springboot.dto.UserRegisterDTO;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PostMapping("/login")
    public ResponseEntity<User> LoginUser(@ModelAttribute UserLoginDTO userdto) {
        User loginuser = userService.getUserByEmailAndPassword(userdto);
        return ResponseEntity.ok(loginuser);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUserRegister(@ModelAttribute UserRegisterDTO userdto) {
        User savedUser = userService.createUserRegister(userdto);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User savedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(savedUser);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable int id) {
//        String message = userService.deleteUser(id);
//        return ResponseEntity.ok(message);
//    }
//

}

