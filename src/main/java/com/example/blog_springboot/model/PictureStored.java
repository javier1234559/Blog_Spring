package com.example.blog_springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PictureStored")
public class PictureStored {

    @Id
    private String name ;

    @Column(name = "image", columnDefinition="LONGBLOB")
    private byte[] image;

}
