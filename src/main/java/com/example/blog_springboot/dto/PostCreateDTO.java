package com.example.blog_springboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDTO {

    @NotNull
    private MultipartFile data;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String category;
}
