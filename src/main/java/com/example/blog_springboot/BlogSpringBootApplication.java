package com.example.blog_springboot;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BlogSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSpringBootApplication.class, args);
    }

//    @Bean
//    public Cloudinary cloudinary() {
//        // Configure
//        Map config = new HashMap();
//        config.put("cloud_name", "divbeelkf");
//        config.put("api_key", "978872644137194");
//        config.put("api_secret", "5BppWv8RGty_imxsQkstKiFnYn4");
//        config.put("secure", true);
//        Cloudinary cloudinary = new Cloudinary(config);
//
//        return cloudinary;
//    }


}


