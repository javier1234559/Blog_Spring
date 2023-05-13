package com.example.blog_springboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO { //This DTO not having pass

    @NotNull
    private int iduser;

    private String name;

    private String email;
    private String phone;
    private String description;
    private String imageurl;
    private int status;
    private String role;

}
