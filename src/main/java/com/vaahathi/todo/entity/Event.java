package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Event extends CommonBaseEntity {
    private boolean isParent;
    private String name;
    private String taskType = "event";

}
