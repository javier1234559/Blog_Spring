package com.example.blog_springboot.controller;

import com.example.blog_springboot.service.BannerService;
import com.example.blog_springboot.service.PostService;
import com.example.blog_springboot.service.TopPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService ;

//    @GetMapping
//    public ResponseEntity<List<?>> getAllTopPost() {
//        return ResponseEntity.ok(topPostService.getAllTopPostsDTO());
//    }
//
//    @PostMapping("/createBanner")
//    public ResponseEntity<?> addTopPost(@RequestParam("idpost") int postId) {
//        PostDetailDTO post = postService.getPostById(postId);
//        if (post == null) {
//            return ResponseEntity.badRequest().body("Invalid post ID");
//        }
//        if(topPostService.checkExistPost(postId)){
//            return ResponseEntity.badRequest().body("Exist Post");
//        }
//        topPostService.addTopPost(postId);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTopPost(@PathVariable("id") int id) {
//        topPostService.deleteTopPost(id);
//        return ResponseEntity.ok().build();
//    }

}
