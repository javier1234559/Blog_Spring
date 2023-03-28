package com.example.blog_springboot.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpost")
    private Post post;

    @Column(nullable = false)
    private String name;
}