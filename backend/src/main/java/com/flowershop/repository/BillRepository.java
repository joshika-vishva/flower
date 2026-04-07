package com.flowershop.repository;

import com.flowershop.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    void deleteByDateBefore(java.time.LocalDateTime date);
}
