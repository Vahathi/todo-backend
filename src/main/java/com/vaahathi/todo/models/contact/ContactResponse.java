package com.vaahathi.todo.models.contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
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
