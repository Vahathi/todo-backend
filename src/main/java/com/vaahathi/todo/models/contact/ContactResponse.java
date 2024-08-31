package com.vaahathi.todo.models.contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private UUID id;
    private String personName;
    private String nickName;
    private long phoneNumber;
    private long altPhoneNumber;
    private String personalEMail;
    private String officeEmail;
    private String webpage;
    private UUID ownerId;
    private String note;

}
