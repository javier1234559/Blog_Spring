package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        StringBuilder str = new StringBuilder();
        for (Post post : posts) {
            str.append(post.toString()).append("\n");
        }
        model.addAttribute("users", str.toString());
        return "test";
    }
    @GetMapping("/list")
    public String getAllPostsList(final Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("listPost", posts);
        return "product/index";
    }

//    @GetMapping("/{id}")
//    public Post getPostById(@PathVariable int id) {
//        return postRepository.findById(id).orElse(null);
//    }
//
//    @PostMapping("/post")
//    public Post createPost(@RequestBody Post post) {
//        return postRepository.save(post);
//    }
//
//    @PutMapping("/{id}")
//    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
//        post.setIdpost(id);
//        return postRepository.save(post);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletePost(@PathVariable int id) {
//        postRepository.deleteById(id);
//    }
}