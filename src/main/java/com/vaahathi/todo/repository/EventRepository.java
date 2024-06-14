package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event>findByCategoryAndOwnerIdAndIsParent(String category, UUID ownerId, boolean isParent);
}
