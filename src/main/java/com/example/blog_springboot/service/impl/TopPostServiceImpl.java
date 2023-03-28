package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.model.TopPost;
import com.example.blog_springboot.repository.TopPostRepository;
import com.example.blog_springboot.service.TopPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopPostServiceImpl implements TopPostService {
    private final TopPostRepository topPostRepository;

    public TopPostServiceImpl(TopPostRepository topPostRepository) {
        this.topPostRepository = topPostRepository;
    }

    @Override
    public void addTopPost(TopPost topPost) {
        topPostRepository.save(topPost);
    }

    @Override
    public void updateTopPost(TopPost topPost) {
        topPostRepository.save(topPost);
    }

    @Override
    public void deleteTopPost(int id) {
        topPostRepository.deleteById(id);
    }

    @Override
    public List<TopPost> getAllTopPosts() {
        return topPostRepository.findAll();
    }
}