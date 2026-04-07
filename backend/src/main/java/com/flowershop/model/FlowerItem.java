package com.flowershop.model;

import jakarta.persistence.*;

@Entity
public class FlowerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double quantity; // in kg
    private Double price;    // per kg
    private Double total;

    public FlowerItem() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
