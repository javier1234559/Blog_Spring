package com.example.blog_springboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @NotNull
    private int idpost ;

//    @NotNull
//    private int iduser ;

    @NotNull
    private String content;

}
