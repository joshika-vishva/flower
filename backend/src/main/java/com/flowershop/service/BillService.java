package com.flowershop.service;

import com.flowershop.model.Bill;
import com.flowershop.model.FlowerItem;
import com.flowershop.model.Inventory;
import com.flowershop.repository.BillRepository;
import com.flowershop.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {
    private final BillRepository billRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public BillService(BillRepository billRepository, InventoryRepository inventoryRepository) {
        this.billRepository = billRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public Bill createBill(Bill bill) {
        if (bill.getDate() == null) {
            bill.setDate(LocalDateTime.now());
        }
        
        double subtotal = 0.0;
        
        for (FlowerItem item : bill.getItems()) {
            double itemTotal = item.getQuantity() * item.getPrice();
            item.setTotal(itemTotal);
            subtotal += itemTotal;

            // Reduce from inventory
            inventoryRepository.findByFlowerName(item.getName()).ifPresent(inventory -> {
                double newStock = inventory.getStockInKg() - item.getQuantity();
                if (newStock < 0) newStock = 0; // Handle negative stock if needed
                inventory.setStockInKg(newStock);
                inventoryRepository.save(inventory);
            });
        }
        
        bill.setSubtotal(subtotal);
        
        double gstPercentage = bill.getGstPercentage() != null ? bill.getGstPercentage() : 0.0;
        double gstAmount = (subtotal * gstPercentage) / 100;
        bill.setGstAmount(gstAmount);
        
        double discountAmount = bill.getDiscountAmount() != null ? bill.getDiscountAmount() : 0.0;
        
        double grandTotal = subtotal + gstAmount - discountAmount;
        bill.setGrandTotal(grandTotal);
        
        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @Transactional
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 86400000) // Runs every 24 hours
    @Transactional
    public void autoDeleteOldBills() {
        java.time.LocalDateTime oneMonthAgo = java.time.LocalDateTime.now().minusDays(30);
        billRepository.deleteByDateBefore(oneMonthAgo);
        System.out.println("Automated Cleanup: Deleted all bills older than " + oneMonthAgo);
    }

    @Transactional
    public Bill updateBill(Long id, Bill updatedBill) {
        Bill existingBill = billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found"));
        
        existingBill.setCustomerName(updatedBill.getCustomerName());
        if (updatedBill.getDate() != null) existingBill.setDate(updatedBill.getDate());
        
        existingBill.getItems().clear();
        existingBill.getItems().addAll(updatedBill.getItems());
        
        double subtotal = 0.0;
        for (FlowerItem item : existingBill.getItems()) {
            double itemTotal = item.getQuantity() * item.getPrice();
            item.setTotal(itemTotal);
            subtotal += itemTotal;
        }
        existingBill.setSubtotal(subtotal);
        double gstPercentage = existingBill.getGstPercentage() != null ? existingBill.getGstPercentage() : 0.0;
        double gstAmount = (subtotal * gstPercentage) / 100;
        existingBill.setGstAmount(gstAmount);
        double discountAmount = existingBill.getDiscountAmount() != null ? existingBill.getDiscountAmount() : 0.0;
        existingBill.setGrandTotal(subtotal + gstAmount - discountAmount);
        
        return billRepository.save(existingBill);
    }
}
