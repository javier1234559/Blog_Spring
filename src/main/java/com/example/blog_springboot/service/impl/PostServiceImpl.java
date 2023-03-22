package com.example.blog_springboot.service.impl;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

//    @Override
//    public List<Post> getPostsByUserId(Long userId) {
//        return postRepository.findByUserId(userId);
//    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePostById(int id) {
        postRepository.deleteById(id);
    }
}
