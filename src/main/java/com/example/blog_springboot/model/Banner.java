package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idbanner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Lob
    private byte[] image;

}