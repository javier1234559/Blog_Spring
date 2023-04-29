package com.example.blog_springboot;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BlogSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSpringBootApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
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


