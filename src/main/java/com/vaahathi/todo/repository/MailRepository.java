package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MailRepository extends JpaRepository<Mail, UUID> {
    List<Mail> findByOwnerIdAndCategory(UUID ownerId, String category);
}

