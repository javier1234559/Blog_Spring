package com.example.blog_springboot.repository;

import com.example.blog_springboot.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer> {
    @Query("SELECT COUNT(b) > 0 FROM Banner b WHERE b.category = :category")
    boolean existsByCategory(@Param("category") String category);

    Banner findBannerByCategory(String category);
}
