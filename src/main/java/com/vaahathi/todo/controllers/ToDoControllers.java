package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.ToDo;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.repository.ToDoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")

public class ToDoControllers {
    @Autowired
    private ToDoRepository toDoRepository;
    @Operation(
            summary = "Create a new todo",
            description = "Id will be automatically generated in UUID format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ToDo created successfully",
                    content = @Content(schema = @Schema(implementation = ToDo.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})

    @PostMapping("/create")
    public ToDo createToDo(@RequestBody ToDo toDo) {
        toDo.setPurpose(toDo.getPurpose());
        toDo.setAppointmentDate(toDo.getAppointmentDate());
        toDo.setImportant(toDo.isImportant());

        // Save the To-Do to the database
        ToDo savedToDo = toDoRepository.save(toDo);

        return savedToDo;
    }
    @GetMapping("/list")
    public List<ToDo> getToDoList(
            @RequestParam("id") UUID id,
            @RequestParam("taskType") String taskType,
            @RequestParam("category") String category) {
        return toDoRepository.findByIdAndTaskTypeAndCategory(id, taskType, category);
    }
    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable UUID id, @RequestBody ToDo updatedToDo) {
        ToDo existingToDo = (ToDo) toDoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("TODO not found with id: " + id));
        existingToDo.setPurpose(updatedToDo.getPurpose());
        existingToDo.setAppointmentDate(updatedToDo.getAppointmentDate());
        existingToDo.setImportant(updatedToDo.isImportant());
        existingToDo.setTaskType(updatedToDo.getTaskType());
        existingToDo.setTaskScheduled(updatedToDo.isTaskScheduled());
        existingToDo.setCategory(updatedToDo.getCategory());
        existingToDo.setUrgent(updatedToDo.isUrgent());
        existingToDo.setDependency(updatedToDo.isDependency());
        existingToDo.setSubject(updatedToDo.getSubject());
        existingToDo.setPhoneNumber(updatedToDo.getPhoneNumber());
        existingToDo.setToDoHistory(updatedToDo.getToDoHistory());
        existingToDo.setNote(updatedToDo.getNote());
        return toDoRepository.save(existingToDo);
    }

}
