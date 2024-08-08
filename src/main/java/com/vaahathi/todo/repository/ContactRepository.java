package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findByOwnerIdAndCategoryAndPersonName(UUID ownerId, String Category, String personName);
}
