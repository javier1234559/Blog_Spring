package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "pass")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "descrip")
    private String description;

    @Column(name = "imageurl")
    private String imageUrl;

    @Column(name = "status")
    private Integer status;

    @Column(name = "role")
    private Integer role;
}
