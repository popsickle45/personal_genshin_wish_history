package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishes")
public class Wish {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(optional = false)
    @JoinColumn(name = "banner_id")
    private Banner banner;

    @ManyToOne                       // null = a 3★ pull
    @JoinColumn(name = "game_item_id")
    private GameItem gameItem;

    @Column(name = "wished_at", nullable = false)
    private LocalDateTime wishedAt;

    public Long getId() { return id; }
    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
    public Banner getBanner() { return banner; }
    public void setBanner(Banner banner) { this.banner = banner; }
    public GameItem getGameItem() { return gameItem; }
    public void setGameItem(GameItem gameItem) { this.gameItem = gameItem; }
    public LocalDateTime getWishedAt() { return wishedAt; }
    public void setWishedAt(LocalDateTime wishedAt) { this.wishedAt = wishedAt; }

    /** 3, 4 or 5 */
    public int getRarity() { return gameItem == null ? 3 : gameItem.getRarity(); }
}