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

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;

    private String name;
}
