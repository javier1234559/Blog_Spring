package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TopPost")
public class TopPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtoppost;

    @OneToMany(mappedBy = "topPost", cascade = CascadeType.REMOVE)
    private List<Post> posts;

}