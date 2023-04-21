package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idnoti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpost")
    private Post post;

    @Lob
    @Column(length = 10000000)
    private byte[] image;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

}
