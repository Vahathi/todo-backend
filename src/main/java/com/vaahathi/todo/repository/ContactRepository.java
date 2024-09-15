package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    List<Contact> findByOwnerId(UUID ownerId);

    @Query("SELECT c FROM Contact c WHERE c.ownerId = :ownerId AND c.personName LIKE %:search% ORDER BY c.personName ASC")
    List<Contact> searchByOwnerIDAndName(UUID ownerId, String search);
}

