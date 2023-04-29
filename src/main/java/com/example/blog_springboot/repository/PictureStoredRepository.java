package com.example.blog_springboot.repository;

import com.example.blog_springboot.model.PictureStored;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PictureStoredRepository extends JpaRepository<PictureStored,String> {
}
