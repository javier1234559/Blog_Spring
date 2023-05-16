package com.example.blog_springboot.repository;

import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.TopPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TopPostRepository extends JpaRepository <TopPost,Integer>{

    @Query("SELECT t.post FROM TopPost t WHERE t.post.idpost = :idpost")
    Post findPostInTopPost(int idpost);

    @Transactional
    @Modifying
    @Query("DELETE FROM TopPost t WHERE t.post.idpost = :idpost")
    void deleteTopPostByIdPost(int idpost);
}
