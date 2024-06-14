package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.BuySell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BuySellRepository extends JpaRepository<BuySell, UUID> {
}
