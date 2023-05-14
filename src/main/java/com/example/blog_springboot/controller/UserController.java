package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.PostCreateDTO;
import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.dto.UserLoginDTO;
import com.example.blog_springboot.dto.UserRegisterDTO;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.PictureStoredService;
import com.example.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private Environment environment;

    @Value("${default.user.icon.url}")
    private String defaultUserIconUrl;

    @Autowired
    private PictureStoredService pictureStoredService ;

    @Autowired
    private UserService userService;

    @GetMapping("/userImage")
    public ResponseEntity<String> getUserImage(Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            UserDTO user = userService.getUserByEmail(email);
            if (user != null && user.getImageurl() != null) {
                return ResponseEntity.ok(user.getImageurl());
            }
        }
        System.out.println(defaultUserIconUrl);
        return ResponseEntity.ok(defaultUserIconUrl);
    }

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
        if (userService.getUserByEmail(userdto.getEmail()) != null){
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userService.createUserRegister(userdto);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id,  MultipartFile imagefile, @ModelAttribute UserDTO userDTO , Principal principal) {
        String email = userDTO.getEmail() ;

        if (email != null && !email.trim().isEmpty()) {
            System.out.println(principal.getName());
            if (!principal.getName().equals(email)){
                if (userService.getUserByEmail(email) != null ){
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        System.out.println(imagefile);
        if (imagefile != null && !imagefile.isEmpty()) {
            String imageUrl = pictureStoredService.addPictureStoredString(imagefile);
            userDTO.setImageurl(imageUrl);
        }
        UserDTO savedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(savedUser);
    }



}

