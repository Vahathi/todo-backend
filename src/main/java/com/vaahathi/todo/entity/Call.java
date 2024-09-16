package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Call extends CommonBaseEntity {
    private String taskType = "call";
}
