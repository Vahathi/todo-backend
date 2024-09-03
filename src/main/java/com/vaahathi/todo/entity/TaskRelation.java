package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data

public class TaskRelation {
    @Id
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String ref;
}
