package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TopPost")
public class TopPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtoppost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpost")
    private Post post;

}