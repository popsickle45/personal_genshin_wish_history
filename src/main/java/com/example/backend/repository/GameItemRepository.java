package com.example.backend.repository;
import com.example.backend.entity.GameItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    boolean existsByNameIgnoreCase(String name);
    //order by rarity and then alphabetical
    List<GameItem> findAllByOrderByRarityDescNameAsc();


}
