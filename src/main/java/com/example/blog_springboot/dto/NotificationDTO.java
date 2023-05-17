package com.example.blog_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    public int idpost;
    public String name;
    public String imageurl;
    public String content;
}
