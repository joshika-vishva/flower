package com.flowershop.model;

import jakarta.persistence.*;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String flowerName;
    private Double stockInKg;
    private Double lowStockThreshold;

    // Standard Constructor
    public Inventory() {}

    public Inventory(String flowerName, Double stockInKg, Double lowStockThreshold) {
        this.flowerName = flowerName;
        this.stockInKg = stockInKg;
        this.lowStockThreshold = lowStockThreshold;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlowerName() { return flowerName; }
    public void setFlowerName(String flowerName) { this.flowerName = flowerName; }

    public Double getStockInKg() { return stockInKg; }
    public void setStockInKg(Double stockInKg) { this.stockInKg = stockInKg; }

    public Double getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(Double lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }
}
