package com.example.backend.repository;

import com.example.backend.entity.BannerType;
import com.example.backend.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    // History screen: newest first
    List<Wish> findByAccountIdOrderByWishedAtDescIdDesc(Long accountId);

    // Pity and rule checks: all wishes of one banner type, oldest first
    List<Wish> findByAccountIdAndBannerBannerTypeOrderByWishedAtAscIdAsc(Long accountId, BannerType bannerType);

    // Used to block deletes
    boolean existsByGameItemId(Long gameItemId);
    boolean existsByBannerId(Long bannerId);
    boolean existsByAccountId(Long accountId);
}