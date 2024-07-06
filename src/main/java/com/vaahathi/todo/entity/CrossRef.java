package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data

public class CrossRef {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String gearKey;
    private String gearValue;
    private String gearLabel;
}
