package com.example.blog_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class HomeDashboardController {

    @GetMapping("/error")
    public String ErrorDashboard(Model model) {
        return "dashboard/404";
    }

    @GetMapping({"","/"})
    public String HomeDashboard() {
        System.out.print("losfddsfsdfdsfdsfdsfds");
        return "dashboard/setting";
    }

    @GetMapping("/accoundashboard")
    public String AccountDashboard(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/accountdashboard";
    }

    @GetMapping("/updateaccount")
    public String UpdateAccount(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/updateaccount";
    }

    @GetMapping("/postdashboard")
    public String PostDashboard(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/postdashboard";
    }

    @GetMapping("/postaccepted")
    public String PostAccepted(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/postaccepted";
    }

    @GetMapping("/postrejected")
    public String PostRejected(Model model) {
//        List<Post> listPost = postService.getAllPosts();
//        model.addAttribute("listPost", listPost);
        return "dashboard/postrejected";
    }

}
