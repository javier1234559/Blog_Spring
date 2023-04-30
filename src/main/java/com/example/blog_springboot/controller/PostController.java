package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.PostSearchDTO;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
//@CrossOrigin(origins = "http://localhost:8080")
public class PostController {

    @Autowired
    private PostService postService ;

    @GetMapping

    public ResponseEntity<List<PostSearchDTO>> getAllPostSearchDTO() {
        return ResponseEntity.ok(postService.getAllPostSearchDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getPostById(@PathVariable int id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post ){
        return ResponseEntity.ok(postService.createPost(post));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") int id, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(id,post));
    }

}