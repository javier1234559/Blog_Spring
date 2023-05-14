package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iduser")
    private User user;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageurl;

    @Column(nullable = false)
    private Date date;

    private int view;

    @Column(nullable = false)
    private String category;


    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToOne(mappedBy = "post", cascade = CascadeType.REMOVE)
    private TopPost topPost;

}
