package com.vaahathi.todo.models.misc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ScheduledTasksResponse {
    LocalDateTime scheduledDate;
    String object;
}
