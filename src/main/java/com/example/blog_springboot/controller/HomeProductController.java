package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeProductController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String getAllPostsHome(Model model) {
        List<Post> listPost = postService.getAllPosts();
        model.addAttribute("listPost", listPost);
        return "product/index";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "/product/about";
    }
    @GetMapping("/contact")
    public String getContact(){
        return "/product/contact";
    }
    @GetMapping("/createpost")
    public String getCreatePost(){
        return "/product/createpost";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "/product/login";
    }

    @GetMapping("/register")
    public String getRegister(){
        return "/product/register";
    }

    @GetMapping("/updatepost")
    public String getPostSetting(){
        return "/product/updatepost";
    }

    @GetMapping("/usersetting")
    public String getUserSetting(){
        return "/product/usersetting";
    }

    @GetMapping("/forgotpass")
    public String getForgotPass(){
        return "/product/forgotpass";
    }

}
