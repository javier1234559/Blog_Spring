package com.example.blog_springboot.service;

import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.model.TopPost;

import java.util.List;

public interface TopPostService {
    boolean checkExistPost(int idpost);
    void addTopPost(int idpost);
    void updateTopPost(TopPost topPost);
    void deleteTopPost(int id);
    List<TopPost> getAllTopPosts();
    List<PostDetailDTO> getAllTopPostsDTO();
}
