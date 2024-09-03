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

public class SearchResponse {
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String personName;
    private String nickName;
    private long phoneNumber;
    private long altPhoneNumber;
    private String personalEMail;
    private String officeEmail;
}