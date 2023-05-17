package com.example.blog_springboot.repository;

import com.example.blog_springboot.dto.NotificationDTO;
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

    @Query("SELECT NEW com.example.blog_springboot.dto.NotificationDTO(p.idpost, u.name, u.imageurl, c.content) FROM Comment c JOIN c.post p JOIN c.user u WHERE u.email = :email")
    List<NotificationDTO> findAllCommentByUserEmail(String email);

}
