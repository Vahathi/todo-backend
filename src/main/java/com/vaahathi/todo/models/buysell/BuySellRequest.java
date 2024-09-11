package com.vaahathi.todo.models.buysell;

import com.vaahathi.todo.entity.Status;
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
public class BuySellRequest {
    private UUID ownerId;
    private UUID pid;
    private String category;
    private boolean taskScheduled;
    private LocalDateTime scheduledDate;
    private boolean isUrgent;
    private boolean isImportant;
    private String purpose;
    private boolean dependency;
    private String personName;
    private long phoneNumber;
    private String callNote;
    private List<String> parentHierarcy;
    private Status status = Status.INITIATED;
}
