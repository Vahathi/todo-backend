package com.vaahathi.todo.models.misc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
public class ScheduledTasksResponse {
    private LocalDate scheduledDate;
    private List<Map<String, Object>> tasks;  // List of tasks
}
