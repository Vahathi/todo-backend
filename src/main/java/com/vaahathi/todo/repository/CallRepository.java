package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Call;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CallRepository extends JpaRepository<Call, UUID> {
    List<Call>findByOwnerIdAndTaskTypeAndCategory(UUID ownerId, String taskType, String category);
}
