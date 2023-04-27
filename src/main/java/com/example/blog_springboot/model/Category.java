package com.example.blog_springboot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpost")
    private Post post;

    @Column(nullable = false)
    private String name;
}
