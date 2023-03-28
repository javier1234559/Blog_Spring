package com.example.blog_springboot.service;

import com.example.blog_springboot.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comments);
    Comment updateComment(int id, Comment comments);
    void deleteComment(int id);
    Comment getCommentById(int id);
    List<Comment> getAllComment();
//    List<Comment> getCommentByPostId(int postId);
//    List<Comment> getCommentByUserId(int userId);
}
