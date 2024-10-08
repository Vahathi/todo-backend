package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByCategoryAndOwnerIdAndTaskType(String category, UUID ownerId, String taskType);
}





