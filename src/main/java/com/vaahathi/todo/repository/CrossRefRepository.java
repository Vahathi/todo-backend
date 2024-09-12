package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.CrossRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CrossRefRepository extends JpaRepository<CrossRef, UUID> {
    List<CrossRef> findBygearKey(String gearKey);
}
