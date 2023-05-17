package com.example.blog_springboot.controller;

import com.example.blog_springboot.dto.UserDTO;
import com.example.blog_springboot.model.Banner;
import com.example.blog_springboot.model.PictureStored;
import com.example.blog_springboot.service.BannerService;
import com.example.blog_springboot.service.PictureStoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private PictureStoredService pictureStoredService ;

    @Autowired
    private BannerService bannerService ;

    @PostMapping("/uploadbanner")
    public ResponseEntity<?> uploadBanner(@RequestParam("imagefile") MultipartFile imagefile, @ModelAttribute Banner banner) {
        String category = banner.getCategory();

        Banner bannerResult = null ;
        if (bannerService.checkExistCategory(category)) {
            Banner existingBanner = bannerService.findByCategory(category);
            String oldImageUrl = existingBanner.getImageurl();
            pictureStoredService.deletePictureStored(oldImageUrl);

            String newImageUrl = pictureStoredService.addPictureStoredString(imagefile);
            banner.setImageurl(newImageUrl);
            bannerResult = bannerService.updateBanner(banner,category);
        } else {
            if (imagefile != null && !imagefile.isEmpty()) {
                String imageUrl = pictureStoredService.addPictureStoredString(imagefile);
                banner.setImageurl(imageUrl);
            } else {
                return ResponseEntity.badRequest().body("No image file provided.");
            }

            bannerResult = bannerService.createBanner(banner);
        }

        return ResponseEntity.ok(bannerResult);
    }

    @GetMapping("/{category}")
    public ResponseEntity<?> getBannerByCategory(@PathVariable("category") String category) {
        Banner banner = bannerService.findByCategory(category);
        if(banner != null){
            return ResponseEntity.ok(banner);
        }
        return ResponseEntity.badRequest().build();
    }


}
