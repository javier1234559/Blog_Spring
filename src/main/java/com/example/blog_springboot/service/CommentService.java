package com.example.blog_springboot.service;

import com.example.blog_springboot.dto.CommentDTO;
import com.example.blog_springboot.model.Comment;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {
    List<Object> getAllComment();
    List<Object> getAllCommentByPost(int id);
    CommentDTO createComment(CommentDTO comments , Authentication authentication);
    int getCommentCount();
}
