package com.example.blog_springboot.service.impl;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(int id, Post post) {
        Post updatedPost = postRepository.findById(id).orElse(null);
        if (updatedPost != null) {
            updatedPost.setTitle(post.getTitle());
            updatedPost.setContent(post.getContent());
            updatedPost.setImage(post.getImage());
            updatedPost.setDate(post.getDate());
            updatedPost.setView(post.getView());
            postRepository.save(updatedPost);
        }
        return updatedPost;
    }

    @Override
    public void deletePost(int id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
//
//    @Override
//    public List<Post> getPostsByUserId(int userId) {
//        return postRepository.findByUserId(userId);
//    }
}