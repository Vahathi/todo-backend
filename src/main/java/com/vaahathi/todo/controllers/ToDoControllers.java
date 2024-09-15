package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.ToDo;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.todo.ToDoRequest;
import com.vaahathi.todo.models.todo.ToDoResponse;
import com.vaahathi.todo.repository.ToDoRepository;
import com.vaahathi.todo.service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/todos")

public class ToDoControllers {
    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private ToDoService toDoService;
    @Autowired
    private ModelMapper modelMapper;

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
    public ResponseEntity<ToDoResponse> createToDo(@RequestBody ToDoRequest toDoRequest) throws Exception {
        ToDoResponse toDoResponse = toDoService.createToDoAndUpdateTaskRel(toDoRequest);
        return ResponseEntity.ok(toDoResponse);
    }

    //    public ResponseEntity <ToDoResponse> createToDo(@RequestBody ToDoRequest toDoRequest) {
//        toDoRequest.setPurpose(toDoRequest.getPurpose());
//        toDoRequest.setAppointmentDate(toDoRequest.getAppointmentDate());
//        toDoRequest.setImportant(toDoRequest.isImportant());
//        ToDo toDo = modelMapper.map(toDoRequest, ToDo.class);
//        // Save the To-Do to the database
//        ToDo savedToDo = toDoRepository.save(toDo);
//        ToDoResponse response = modelMapper.map(savedToDo, ToDoResponse.class);
//        return ResponseEntity.ok(response);
    //  }
    @GetMapping("/list")
    public ResponseEntity<List<ToDoResponse>> getToDoList(
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("category") String category) {
        List<ToDo> todos = toDoRepository.findByOwnerIdAndCategory(ownerId, category);
        todos.sort(Comparator.comparingInt(todo -> calculatePriority(todo.isImportant(), todo.isUrgent())));
        Type listType = new TypeToken<List<ToDoResponse>>() {
        }.getType();
        List<ToDoResponse> todoResponses = modelMapper.map(todos, listType);
        return ResponseEntity.ok(todoResponses);
    }

    private int calculatePriority(boolean isImportant, boolean isUrgent) {
        if (isImportant && isUrgent) {
            return 1;  // Highest priority
        } else if (!isImportant && isUrgent) {
            return 2;  // Second priority
        } else if (isImportant && !isUrgent) {
            return 3;  // Third priority
        } else {
            return 4;  // Lowest priority
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoResponse> updateToDo(@PathVariable UUID id, @RequestBody ToDoRequest updatedToDoRequest) {
        ToDo existingToDo = (ToDo) toDoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("TODO not found with id: " + id));
        modelMapper.map(updatedToDoRequest, existingToDo);

        ToDo updatedToDo = toDoRepository.save(existingToDo);

        ToDoResponse toDoResponse = modelMapper.map(updatedToDo, ToDoResponse.class);

        return ResponseEntity.ok(toDoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable UUID id) {
        Optional<ToDo> toDo = toDoRepository.findById(id);
        if (toDo.isPresent()) {
            return ResponseEntity.ok(toDo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

