package com.vaahathi.todo.repository;

import com.vaahathi.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ToDoRepository extends JpaRepository<ToDo,UUID> {
    List<ToDo> findByIdAndTaskTypeAndCategory(UUID Id, String taskType, String category);
}


