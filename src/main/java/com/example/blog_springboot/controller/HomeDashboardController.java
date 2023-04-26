package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class HomeDashboardController {

    @GetMapping("/error")
    public String ErrorDashboard() {
        return "dashboard/404";
    }

    @GetMapping({"","/"})
    public String HomeDashboard() {
        return "dashboard/setting";
    }

    @GetMapping("/accoundashboard")
    public String AccountDashboard() {
        return "dashboard/accountdashboard";
    }

    @GetMapping("/updateaccount")
    public String UpdateAccount() {
        return "dashboard/updateaccount";
    }

    @GetMapping("/postdashboard")
    public String PostDashboard() {
        return "dashboard/postdashboard";
    }

    @GetMapping("/postaccepted")
    public String PostAccepted() {
        return "dashboard/postaccepted";
    }

    @GetMapping("/postrejected")
    public String PostRejected() {
        return "dashboard/postrejected";
    }





}
