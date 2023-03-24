package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpost;

    @Column(nullable = false)
    private String title;

    private String imageurl;

    private String content;

    @Column(nullable = false)
    private Date date;

    private Long view;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;

}
