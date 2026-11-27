package com.example.backend.controller;

import com.example.backend.entity.GameItem;
import com.example.backend.entity.ItemType;
import com.example.backend.entity.Pool;
import com.example.backend.repository.BannerRepository;
import com.example.backend.repository.GameItemRepository;
import com.example.backend.repository.WishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class GameItemController {

    private final GameItemRepository items;
    private final WishRepository wishes;
    private final BannerRepository banners;

    public GameItemController(GameItemRepository items, WishRepository wishes, BannerRepository banners) {
        this.items = items;
        this.wishes = wishes;
        this.banners = banners;
    }

    /** What the app sends when adding or editing an item */
    record ItemRequest(String name, ItemType itemType, Integer rarity, Pool pool) {}

    @GetMapping
    public List<GameItem> all() {
        return items.findAllByOrderByRarityDescNameAsc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameItem create(@RequestBody ItemRequest req) {
        validate(req);
        String name = req.name().trim();
        if (items.existsByNameIgnoreCase(name))
            throw error(HttpStatus.CONFLICT, "An item named '" + name + "' already exists");

        GameItem item = new GameItem();
        item.setName(name);
        item.setItemType(req.itemType());
        item.setRarity(req.rarity());
        item.setPool(req.pool());
        return items.save(item);
    }

    @PutMapping("/{id}")
    public GameItem update(@PathVariable Long id, @RequestBody ItemRequest req) {
        GameItem item = items.findById(id)
                .orElseThrow(() -> error(HttpStatus.NOT_FOUND, "Item not found"));

        if (item.isBuiltin()) {
            // Built-in items: only the pool can change (e.g. a limited character joining Standard)
            if (req.pool() == null) throw error(HttpStatus.BAD_REQUEST, "Pool is required");
            item.setPool(req.pool());
            return items.save(item);
        }

        validate(req);
        String name = req.name().trim();
        if (!name.equalsIgnoreCase(item.getName()) && items.existsByNameIgnoreCase(name))
            throw error(HttpStatus.CONFLICT, "An item named '" + name + "' already exists");

        item.setName(name);
        item.setItemType(req.itemType());
        item.setRarity(req.rarity());
        item.setPool(req.pool());
        return items.save(item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        GameItem item = items.findById(id)
                .orElseThrow(() -> error(HttpStatus.NOT_FOUND, "Item not found"));
        if (item.isBuiltin())
            throw error(HttpStatus.CONFLICT, "Built-in items can't be deleted");
        if (wishes.existsByGameItemId(id))
            throw error(HttpStatus.CONFLICT, "This item appears in your wish history");
        if (banners.existsByFeaturedItemsId(id))
            throw error(HttpStatus.CONFLICT, "This item is featured on a banner");
        items.delete(item);
    }

    private void validate(ItemRequest req) {
        if (req.name() == null || req.name().isBlank())
            throw error(HttpStatus.BAD_REQUEST, "Name is required");
        if (req.itemType() == null)
            throw error(HttpStatus.BAD_REQUEST, "Type is required (CHARACTER or WEAPON)");
        if (req.rarity() == null || (req.rarity() != 4 && req.rarity() != 5))
            throw error(HttpStatus.BAD_REQUEST, "Rarity must be 4 or 5");
        if (req.pool() == null)
            throw error(HttpStatus.BAD_REQUEST, "Pool is required (STANDARD or LIMITED)");
    }

    private static ResponseStatusException error(HttpStatus status, String message) {
        return new ResponseStatusException(status, message);
    }
}