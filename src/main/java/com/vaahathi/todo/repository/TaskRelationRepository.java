package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.TaskRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRelationRepository extends JpaRepository<TaskRelation, UUID> {

    @Query(value="select r.ref as tableName, array_agg(id) as childrenId  from task_relation r where pid = :pid GROUP BY ref",nativeQuery = true)
    List<Object[]> GetSubtasksRelations(UUID pid);
}





