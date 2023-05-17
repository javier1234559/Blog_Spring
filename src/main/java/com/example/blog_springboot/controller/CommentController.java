package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.CommentDTO;
import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.PostSearchDTO;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.service.CommentService;
import com.example.blog_springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/post/comments")
public class CommentController {

    @Autowired
    private CommentService commentService ;

    @GetMapping
    public ResponseEntity<List<?>> getAllComment() {
        return ResponseEntity.ok(commentService.getAllComment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<?>> getAllCommentByPost(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getAllCommentByPost(id));
    }

    @GetMapping("/allcommentByUser")
    public ResponseEntity<List<?>> getAllCommentByUser(Principal principal) {
        if(principal == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(commentService.getAllCommentByUserEmail(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> createComment(@ModelAttribute CommentDTO comment , Authentication authentication){
        System.out.print(comment);
        return ResponseEntity.ok(commentService.createComment(comment,authentication));
    }

}
