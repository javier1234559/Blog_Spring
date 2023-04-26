package com.example.blog_springboot.service.impl;
import com.example.blog_springboot.exception.ResourceExistException;
import com.example.blog_springboot.exception.ResourceNotFoundException;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.ultilies.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private  PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post createPost(Post post) {
        try {
            return postRepository.save(post);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Error creating post", ex);
        }
    }

    @Override
    public Post updatePost(int id, Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post updatedPost = optionalPost.get();
            updatedPost.setTitle(post.getTitle());
            updatedPost.setContent(post.getContent());
            updatedPost.setImage(post.getImage());
            updatedPost.setDate(post.getDate());
            updatedPost.setView(post.getView());
            updatedPost.setStatus(post.getStatus());
            postRepository.save(updatedPost);
            return updatedPost;
        } else {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
    }

    public String deletePost(int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            postRepository.deleteById(id);
            return "Post" + Constant.DELETE_SUCCESSFULLY;
        } else {
            throw new ResourceExistException("Post " + Constant.DELETE_FAILED);
        }
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) {
            return post.get();
        } else {
            throw new ResourceNotFoundException(Constant.NOT_FOUND_WITH_ID + id);
        }
    }

//
//    @Override
//    public List<Post> getPostsByUserId(int userId) {
//        return postRepository.findByUserId(userId);
//    }
}