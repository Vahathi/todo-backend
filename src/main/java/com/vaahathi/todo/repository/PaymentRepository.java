package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOwnerIdAndTaskTypeAndCategory(UUID ownerId, String taskType, String category);

    @Query("SELECT c FROM Call c ORDER BY " +
            "CASE WHEN c.isImportant = true AND c.isUrgent = true THEN 1 " +
            "WHEN c.isImportant = false AND c.isUrgent = true THEN 2 " +
            "WHEN c.isImportant = true AND c.isUrgent = false THEN 3 " +
            "ELSE 4 END")
    List<Payment> findAllOrderByCustomPriority();
}
