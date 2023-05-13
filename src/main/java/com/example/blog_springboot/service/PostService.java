package com.example.blog_springboot.service;

import com.example.blog_springboot.dto.PostCreateDTO;
import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.PostSearchDTO;
import com.example.blog_springboot.model.Post;

import java.security.Principal;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    List<PostSearchDTO> getAllPostSearchDTO();
    List<PostDetailDTO> getAllPostDetailDTO();
    List<PostDetailDTO> getPostByEmailUser(String email);
    PostDetailDTO getPostById(int id);
    Post createPost(Post post);
    Post createPostDTO(PostCreateDTO post , Principal principal);
    Object updatePost(int id, PostCreateDTO postDTO);
    String deletePost(int id);
}
