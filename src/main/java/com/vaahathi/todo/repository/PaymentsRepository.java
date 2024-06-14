package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
}
