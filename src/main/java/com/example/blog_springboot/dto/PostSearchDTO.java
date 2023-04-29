package com.example.blog_springboot.dto;

import com.example.blog_springboot.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDTO {
    private int idpost;
    private String iduser;
    private String userName;
    private String title;
    private String imageurl;
    private Date date;
    private int view;
    private String category;

}
