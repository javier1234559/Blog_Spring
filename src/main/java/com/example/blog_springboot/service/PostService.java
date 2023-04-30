package com.example.blog_springboot.service;

import com.example.blog_springboot.dto.PostCreateDTO;
import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.PostSearchDTO;
import com.example.blog_springboot.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    List<PostSearchDTO> getAllPostSearchDTO();
    List<PostDetailDTO> getAllPostDetailDTO();
    PostDetailDTO getPostById(int id);
    Post createPost(Post post);
    Post createPostDTO(PostCreateDTO post);
    Post updatePost(int id, Post post);
    String deletePost(int id);
}
