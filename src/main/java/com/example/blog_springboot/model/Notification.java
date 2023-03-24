package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idnoti;

    @Column(name = "imageurl")
    private String imageurl;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;


}
