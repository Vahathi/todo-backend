package com.vaahathi.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Entity
@Data
public class Contact extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;
   private String personName;
   private String nickName;
   private long phoneNumber;
   private long altPhoneNumber;
   private String personalEMail;
   private String officeEmail;
   private String webpage;
   private String taskType="contact";
   private String category;
   private UUID ownerId;

}
