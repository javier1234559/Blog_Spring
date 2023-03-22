package com.example.blog_springboot.service;

import com.example.blog_springboot.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(int id);

//    List<Post> getPostsByUserId(Long userId);

    void savePost(Post post);

    void updatePost(Post post);

    void deletePostById(int id);
}
