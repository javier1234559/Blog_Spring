package com.example.blog_springboot.service;

import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.model.TopPost;

import java.util.List;

public interface TopPostService {
    void addTopPost(TopPost topPost);
    void updateTopPost(TopPost topPost);
    void deleteTopPost(int id);
    List<TopPost> getAllTopPosts();

    List<PostDetailDTO> getAllTopPostsDTO();
}
