package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.dto.PostDetailDTO;
import com.example.blog_springboot.dto.PostSearchDTO;
import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.TopPost;
import com.example.blog_springboot.repository.PostRepository;
import com.example.blog_springboot.repository.TopPostRepository;
import com.example.blog_springboot.service.TopPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopPostServiceImpl implements TopPostService {
    @Autowired
    private  TopPostRepository topPostRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public boolean checkExistPost(int idpost) {
        if(topPostRepository.findPostInTopPost(idpost) != null) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void addTopPost(int idpost) {
        Post post = postRepository.getById(idpost);
        TopPost topPost = new TopPost();
        topPost.setIdtoppost(idpost);
        topPost.setPost(post);
        topPostRepository.save(topPost);
    }

    @Override
    public void updateTopPost(TopPost topPost) {
        topPostRepository.save(topPost);
    }

    @Override
    public void deleteTopPost(int id) {
        topPostRepository.deleteTopPostByIdPost(id);
    }

    @Override
    public List<TopPost> getAllTopPosts() {
        return topPostRepository.findAll();
    }

    @Override
    public List<PostDetailDTO> getAllTopPostsDTO() {
        List<TopPost> list = topPostRepository.findAll();
        List<PostDetailDTO> dtoList = list.stream()
                .map(post -> mapper.map(post, PostDetailDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }
}