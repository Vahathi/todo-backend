package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CallRepository extends JpaRepository<Call, UUID> {
    List<Call> findByOwnerIdAndCategory(UUID ownerId, String category);
}
