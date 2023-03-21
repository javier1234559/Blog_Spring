package com.example.blog_springboot.controller;

import com.example.blog_springboot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

//    @GetMapping("")
//    public List<Post> getAllPosts() {
//        return postRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Post getPostById(@PathVariable Long id) {
//        return postRepository.findById(id).orElse(null);
//    }
//
//    @PostMapping("")
//    public Post createPost(@RequestBody Post post) {
//        return postRepository.save(post);
//    }
//
//    @PutMapping("/{id}")
//    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
//        post.setIdpost(id);
//        return postRepository.save(post);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletePost(@PathVariable Long id) {
//        postRepository.deleteById(id);
//    }
}