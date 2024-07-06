package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.CrossRef;
import com.vaahathi.todo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface CrossRefRepository extends JpaRepository<CrossRef, String> {
    List<CrossRef> findBygearKey(String gearKey);
}
