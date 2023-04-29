package com.example.blog_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDTO {

    private MultipartFile data;

    private String title;

    private String content;

    private String category;
}
