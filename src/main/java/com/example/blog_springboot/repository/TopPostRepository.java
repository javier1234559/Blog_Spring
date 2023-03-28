package com.example.blog_springboot.repository;

import com.example.blog_springboot.model.TopPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopPostRepository extends JpaRepository <TopPost,Integer>{

}
