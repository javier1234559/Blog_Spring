package com.example.blog_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailDTO {
    private int idpost;
    private String iduser;
    private String userName;
    private String title;
    private String imageurl;
    private Date date;
    private int content;
    private int view;
    private String category;
}
