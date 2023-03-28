package com.example.blog_springboot.controller;

import com.example.blog_springboot.model.Post;
import com.example.blog_springboot.model.Test;
import com.example.blog_springboot.service.PostService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Controller
public class TestController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String Home (){
        return "test";
    }
    @PostMapping ("/test")
    public String savePost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("image") MultipartFile image) {
        try {
            Date currentDate = new Date();
            Post post = new Post();
            post.setDate(currentDate);
            post.setTitle(title);
            post.setContent(content);
            System.out.println(image);
            MultipartFile in = image ;
            byte[] in2 = image.getBytes();
            post.setImage(in2);
            postService.updatePost(1,post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/display";
    }
    @GetMapping("/display")
    public String display(){
        return  "display";
    }

//    @GetMapping("/upload")
//    public ResponseEntity<byte[]> getImage() {
//
//        if (test != null) {
//            byte[] image = test.getImage();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            return new ResponseEntity<>(image, headers, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


}
