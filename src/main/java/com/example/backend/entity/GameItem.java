package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game_items")
public class GameItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(nullable = false)
    private int rarity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Pool pool;

    @Column(name = "is_builtin", nullable = false)
    private boolean builtin;

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    public int getRarity() { return rarity; }
    public void setRarity(int rarity) { this.rarity = rarity; }
    public Pool getPool() { return pool; }
    public void setPool(Pool pool) { this.pool = pool; }
    public boolean isBuiltin() { return builtin; }
}