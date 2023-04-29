package com.example.blog_springboot.service;

import com.example.blog_springboot.model.PictureStored;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureStoredService {

    PictureStored getPictureStored(String name);

    String addPictureStoredString(MultipartFile file);

    PictureStored addPictureStored(PictureStored picture);

    PictureStored updatePictureStored(PictureStored picture);

    String deletePictureStored(String id);

    List<PictureStored> getAllPictureStoreds();

}
