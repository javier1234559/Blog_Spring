package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.model.Banner;
import com.example.blog_springboot.model.PictureStored;
import com.example.blog_springboot.repository.BannerRepository;
import com.example.blog_springboot.repository.PictureStoredRepository;
import com.example.blog_springboot.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository ;

    @Override
    public boolean checkExistCategory(String category) {
        return bannerRepository.existsByCategory(category);
    }

    @Override
    public Banner findByCategory(String category) {
        return bannerRepository.findBannerByCategory(category);
    }

    @Override
    public Banner createBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(Banner banner, String category ) {
        Banner updateBanner = bannerRepository.findBannerByCategory(category);
        updateBanner.setImageurl(banner.getImageurl());
        updateBanner.setTitle(banner.getTitle());
        updateBanner.setSubtitle(banner.getSubtitle());
        return bannerRepository.save(updateBanner);
    }
}
