package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class HomeDashboardController {

    @GetMapping("/error")
    public String ErrorDashboard(Model model) {
        return "dashboard/404";
    }

    @GetMapping({"","/"})
    public String HomeDashboard(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/setting";
    }

    @GetMapping("/account")
    public String AccountDashboard(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/accountdashboard";
    }

    @GetMapping("/post")
    public String PostDashboard(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/postdashboard";
    }


}
