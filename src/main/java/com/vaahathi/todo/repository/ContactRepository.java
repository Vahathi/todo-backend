package com.vaahathi.todo.repository;
import com.vaahathi.todo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findByOwnerIdAndTaskType(UUID ownerId, String taskType);
    @Query(value = "SELECT * FROM contact WHERE person_Name ILIKE %:personName% OR similarity(person_Name, :personName) > 0.1 ORDER BY similarity(person_Name, :personName) DESC", nativeQuery = true)
    List<Contact> findByNameFuzzy(@Param("personName") String personName);
}


