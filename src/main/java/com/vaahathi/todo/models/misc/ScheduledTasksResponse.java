package com.vaahathi.todo.models.misc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
public class ScheduledTasksResponse {
    LocalDateTime scheduledDate;
    private Map<String, Object> object;
}
