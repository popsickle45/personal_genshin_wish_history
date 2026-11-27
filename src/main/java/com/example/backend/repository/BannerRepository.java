package com.example.backend.repository;

import com.example.backend.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BannerRepository extends JpaRepository<Banner,Long> {
    //newest banner first
    List<Banner> findALlByOrderByStartDateDesc();
    //checks if it already exists and prevents deleting history
    boolean existsByFeaturedItemsId(Long gameItemId);
}
