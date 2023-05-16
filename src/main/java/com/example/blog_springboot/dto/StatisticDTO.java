package com.example.blog_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    private int postCount;
    private int viewCount;
    private int commentCount;
    private int pendingPostCount;
}
