package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.dto.CommentDTO;
import com.example.blog_springboot.model.Comment;
import com.example.blog_springboot.model.User;
import com.example.blog_springboot.repository.CommentRepository;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.repository.UserRepository;
import com.example.blog_springboot.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository ;


    @Override
    public List<Object> getAllComment() { //anonymous object to get image and name user
        List<Comment> list = commentRepository.findAll();
        List<Object> resultList = list.stream()
                .map(comment -> new Object() {
                    public int idpost = comment.getPost().getIdpost();
                    public int iduser = comment.getUser().getIduser();
                    public String content = comment.getContent();
                    public String imageurl = comment.getUser().getImageurl();
                    public String name = comment.getUser().getName();
                    public Date date = comment.getDate();
                })
                .collect(Collectors.toList());
        return resultList;
    }

    @Override
    public List<Object> getAllCommentByPost(int id) {
        List<Comment> list = commentRepository.findByPostId(id);
        List<Object> resultList = list.stream()
                .map(comment -> new Object() {
                    public int idpost = comment.getPost().getIdpost();
                    public int iduser = comment.getUser().getIduser();
                    public String content = comment.getContent();
                    public String imageurl = comment.getUser().getImageurl();
                    public String name = comment.getUser().getName();
                    public Date date = comment.getDate();
                })
                .collect(Collectors.toList());
        return resultList;
    }


    @Override
    public CommentDTO createComment(CommentDTO commentdto,Authentication authentication) {

        try {
            Comment comment =  mapper.map(commentdto,Comment.class); // just mapped content prop
            int idpost = commentdto.getIdpost();
            Optional<User> user = userRepository.findByEmail(authentication.getName());
            comment.setPost(postRepository.getById(idpost));
            comment.setUser(user.get());
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            comment.setDate(sqlDate);
            commentRepository.save(comment);
            return  commentdto ;
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error creating comment", ex);
        }
    }


}
