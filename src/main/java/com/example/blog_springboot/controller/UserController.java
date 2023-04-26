package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.User;
import com.example.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService ;

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        // code để lấy thông tin user với id tương ứng
        User user = new User();
//        user.setId(id);
//        user.setName("John Doe");
//        user.setEmail("johndoe@example.com");
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // code để tạo mới user với thông tin từ request body
        // và trả về user đã tạo
       // user.setId(1L);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        // code để cập nhật thông tin user với id tương ứng từ request body
        // và trả về user đã cập nhật
       // user.setId(id);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        // code để xóa user với id tương ứng
    }
}

