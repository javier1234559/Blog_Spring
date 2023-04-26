package com.example.blog_springboot.service;

import com.example.blog_springboot.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
//    List<Post> getPostsByUserId(int userId);
    Post getPostById(int id);
    Post createPost(Post post);
    Post updatePost(int id, Post post);
    String deletePost(int id);
}
