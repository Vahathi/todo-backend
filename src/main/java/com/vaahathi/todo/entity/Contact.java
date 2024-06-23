package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Data
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;
   private String name;
   private String nickName;
   private long phone;
   private long altPhone;
   private String personalEMail;
   private String officeEmail;
   private String webpage;
   private String taskType;
   private String category;
   private UUID ownerId;
   private String note;
}
