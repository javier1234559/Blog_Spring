package com.example.blog_springboot.repository;

import com.example.blog_springboot.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query("SELECT p FROM Post p WHERE p.user.email = :email")
    List<Post> findPostsByUserEmail(@Param("email") String email);

    @Query("SELECT p FROM Post p WHERE p.status = :status")
    List<Post> findByStatus(@Param("status") String status);

}
