package com.example.blog_springboot.service.impl;

import com.example.blog_springboot.exception.ResourceExistException;
import com.example.blog_springboot.exception.ResourceNotFoundException;
import com.example.blog_springboot.model.PictureStored;
import com.example.blog_springboot.model.PictureStored;
import com.example.blog_springboot.repository.PictureStoredRepository;
import com.example.blog_springboot.service.PictureStoredService;
import com.example.blog_springboot.ultilies.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PictureStoredServiceImpl implements PictureStoredService {

    @Autowired
    private PictureStoredRepository pictureStoredRepository ;

    @Override
    public List<PictureStored> getAllPictureStoreds() {
        return pictureStoredRepository.findAll();
    }

    @Override
    public PictureStored getPictureStored(String name) {

        Optional<PictureStored> pic = pictureStoredRepository.findById(name);
        if(pic.isPresent()) {
            return pic.get();
        } else {
            throw new ResourceNotFoundException(Constant.NOT_FOUND_WITH_ID + name);
        }
    }

    @Override
    public String addPictureStoredString(MultipartFile file) {
        String randomNameImage = null ;
        try {
            byte[] convertToByte = file.getBytes();
            randomNameImage = UUID.randomUUID().toString();
            PictureStored newpic = new PictureStored();
            newpic.setImage(convertToByte);
            newpic.setName(randomNameImage);
            pictureStoredRepository.save(newpic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return randomNameImage;
    }


    @Override
    public String deletePictureStored(String id) {
        Optional<PictureStored> post = pictureStoredRepository.findById(id);
        if (post.isPresent()) {
            pictureStoredRepository.deleteById(id);
            return "PictureStored" + Constant.DELETE_SUCCESSFULLY;
        } else {
            throw new ResourceExistException("PictureStored " + Constant.DELETE_FAILED);
        }
    }


}
