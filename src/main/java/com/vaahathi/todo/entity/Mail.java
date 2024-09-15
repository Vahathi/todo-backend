package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Mail extends CommonBaseEntity {
    private String eMailId;

}
