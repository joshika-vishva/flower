package com.flowershop;

import com.flowershop.model.Inventory;
import com.flowershop.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowerBillingApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowerBillingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(InventoryRepository repository) {
        return args -> {
            if (repository.findByFlowerName("அரளி").isEmpty()) {
                Inventory item = new Inventory();
                item.setFlowerName("அரளி");
                item.setStockInKg(100.0);
                item.setLowStockThreshold(5.0);
                repository.save(item);
            }
            if (repository.findByFlowerName("ஹைப்ரிட் அரலி").isEmpty()) {
                Inventory item = new Inventory();
                item.setFlowerName("ஹைப்ரிட் அரலி");
                item.setStockInKg(100.0);
                item.setLowStockThreshold(5.0);
                repository.save(item);
            }
        };
    }
}

