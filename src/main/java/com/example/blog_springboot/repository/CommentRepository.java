package com.example.blog_springboot.repository;

import com.example.blog_springboot.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query("SELECT c FROM Comment c JOIN c.post p WHERE p.idpost = :idpost")
    List<Comment> findByPostId(int idpost);

    @Query("SELECT COUNT(c.idcomment) FROM Comment c JOIN c.post p WHERE p.idpost = :idpost")
    int getCommentQuantityByPost(int idpost);

}
