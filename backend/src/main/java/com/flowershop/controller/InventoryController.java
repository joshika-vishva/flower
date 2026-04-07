package com.flowershop.controller;

import com.flowershop.model.Inventory;
import com.flowershop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.addOrUpdateInventory(inventory));
    }
}
