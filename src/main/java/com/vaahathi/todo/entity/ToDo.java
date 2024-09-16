package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class ToDo extends CommonBaseEntity {
    private String taskType = "toDo";

}
