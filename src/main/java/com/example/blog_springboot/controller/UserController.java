package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.PostCreateDTO;
import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.dto.UserLoginDTO;
import com.example.blog_springboot.dto.UserRegisterDTO;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.EmailService;
import com.example.blog_springboot.service.PictureStoredService;
import com.example.blog_springboot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private EmailService emailService;
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

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

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

    @PostMapping("/forgotpass")
    public ResponseEntity<?> handleForgotPass(HttpSession session, @RequestParam("code") String code , @RequestParam("email") String email, @RequestParam("newpass") String newpass) {
        Map<String, Object> response = new HashMap<>();
        if (userService.getUserByEmail(email) == null){
            response.put("error", "Not found user name with that email !");
            return ResponseEntity.badRequest().body(response);
        }
        String sessionCode = (String) session.getAttribute("code");
        if (code.equals(sessionCode)) {
            System.out.println("Code match");
            userService.resetPassword(email,newpass);
            response.put("message", "Reset password successful");
            return ResponseEntity.ok(response);
        } else {
            System.out.println("Code does not match");
            response.put("error", "Code not match !");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/createuser")
    public ResponseEntity<User> createUserDashboard(@ModelAttribute User user,MultipartFile imagefile,Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if(!isAdmin){
            return ResponseEntity.badRequest().build();
        }
        if (userService.getUserByEmail(user.getEmail()) != null){
            return ResponseEntity.badRequest().build();
        }
        if (imagefile != null && !imagefile.isEmpty()) {
            String imageUrl = pictureStoredService.addPictureStoredString(imagefile);
            user.setImageurl(imageUrl);
        }

        User savedUser = userService.createUserDashBoard(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id,  MultipartFile imagefile, @ModelAttribute UserDTO userDTO , Authentication authentication) {
        String email = userDTO.getEmail() ;

        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && email != null && !email.trim().isEmpty()) {
            if (!authentication.getName().equals(email)) {
                if (userService.getUserByEmail(email) != null) {
                    return ResponseEntity.badRequest().build();
                }
            }
        }

        if (imagefile != null && !imagefile.isEmpty()) {
            String imageUrl = pictureStoredService.addPictureStoredString(imagefile);
            userDTO.setImageurl(imageUrl);
        }
        UserDTO savedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(savedUser);
    }



}

