package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "banners")
public class Banner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "banner_type", nullable = false)
    private BannerType bannerType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_builtin", nullable = false)
    private boolean builtin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "banner_featured_items",
            joinColumns = @JoinColumn(name = "banner_id"),
            inverseJoinColumns = @JoinColumn(name = "game_item_id"))
    private Set<GameItem> featuredItems = new HashSet<>();

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BannerType getBannerType() { return bannerType; }
    public void setBannerType(BannerType bannerType) { this.bannerType = bannerType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public boolean isBuiltin() { return builtin; }
    public Set<GameItem> getFeaturedItems() { return featuredItems; }
}