package com.flowershop.service;

import com.flowershop.model.Inventory;
import com.flowershop.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory addOrUpdateInventory(Inventory inventory) {
        return inventoryRepository.findByFlowerName(inventory.getFlowerName())
                .map(existing -> {
                    existing.setStockInKg(existing.getStockInKg() + inventory.getStockInKg());
                    if (inventory.getLowStockThreshold() != null) {
                        existing.setLowStockThreshold(inventory.getLowStockThreshold());
                    }
                    return inventoryRepository.save(existing);
                })
                .orElseGet(() -> inventoryRepository.save(inventory));
    }
}
