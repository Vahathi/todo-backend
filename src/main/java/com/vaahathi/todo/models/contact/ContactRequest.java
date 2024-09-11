package com.vaahathi.todo.models.contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    private UUID ownerId;
    private String personName;
    private String nickName;
    private long phoneNumber;
    private long altPhoneNumber;
    private String personalEMail;
    private String officeEmail;
    private String webpage;
    private String category;
    private String note;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
}
