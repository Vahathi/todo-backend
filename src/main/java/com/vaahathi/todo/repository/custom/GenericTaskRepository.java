package com.vaahathi.todo.repository.custom;

import com.vaahathi.todo.models.misc.ScheduledTasksResponse;

import java.util.List;
import java.util.UUID;

public interface GenericTaskRepository {
    List<ScheduledTasksResponse> GetScheduledTasks(UUID ownerId, String category);
}


