package com.vaahathi.todo.models.contact;

import java.util.List;
import java.util.UUID;

public class SearchResponse {
    private UUID id;
    private UUID pid;
    private List<UUID> cid;
    private String personName;
    private String nickName;
    private long phoneNumber;
    private long altPhoneNumber;
}