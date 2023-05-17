package com.example.blog_springboot.service;

import com.example.blog_springboot.model.Banner;

public interface BannerService {

    boolean checkExistCategory (String category);

    Banner findByCategory(String category);

    Banner createBanner(Banner banner);

    Banner updateBanner(Banner banner, String category );
}
