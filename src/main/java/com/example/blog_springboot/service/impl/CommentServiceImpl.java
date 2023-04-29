package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.model.Comment;
import com.example.blog_springboot.repository.CommentRepository;
import com.example.blog_springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment getCommentById(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment createComment(Comment comments) {
        return commentRepository.save(comments);
    }

    @Override
    public Comment updateComment(int id, Comment comments) {
        Comment updatedComment = commentRepository.findById(id).orElse(null);
        if (updatedComment != null) {
            updatedComment.setContent(comments.getContent());
            updatedComment.setDate(comments.getDate());
            updatedComment.setPost(comments.getPost());
            updatedComment.setUser(comments.getUser());
            commentRepository.save(updatedComment);
        }
        return updatedComment;
    }

    @Override
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }


}
