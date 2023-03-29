package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService ;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String getAllPosts(Model model) {
        List<Post> listPost = postService.getAllPosts();
        model.addAttribute("listPost", listPost);
        return "product/index";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") int id , Model model) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            Post post = postService.getPostById(id);
            model.addAttribute("post", post);
            return "product/detailpost";
        } else {
            return "product/index";
        }
    }

    @PostMapping("")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post savedPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            postService.deletePost(postService.getPostById(id).getIdpost());
            return ResponseEntity.ok().body("Post has been deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") int id, @RequestBody Post post) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.getPostById(id));
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setImage(post.getImage());
            existingPost.setView(post.getView());
            Post savedPost = postService.updatePost(id,post);
            return ResponseEntity.ok().body(savedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}