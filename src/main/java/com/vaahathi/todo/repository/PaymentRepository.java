package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOwnerIdAndTaskTypeAndCategory(UUID ownerId, String taskType, String category);
}
