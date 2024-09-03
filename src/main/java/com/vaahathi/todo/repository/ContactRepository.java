package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findByOwnerIdAndTaskTypeAndCategory(UUID ownerId, String taskType,String Category);

    @Query("SELECT c FROM Contact c WHERE c.ownerId = :ownerId AND c.personName LIKE %:search%")
    List<Contact> searchByOwnerIDAndName(UUID ownerId, String search);
}

