package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.*;
import com.vaahathi.todo.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/subtasks")
@Tag(name = "subtask", description = "subtask maanagement APIs")
public class SubtaskControllers {


    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    CallRepository callRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    MailRepository mailRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ToDoRepository toDoRepository;
    @Autowired
    TaskRelationRepository taskRelationRepository;
    @Autowired
    private ModelMapper modelMapper;

@Operation(
        summary = "Get subtasks",
        description = "gets all subtasks of a task"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success",
                content = @Content(schema = @Schema(implementation = Appointment.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})

@GetMapping ("/{id}")
public ResponseEntity <List<Object>> getSubtasks(
        @PathVariable("id") UUID id) throws Exception {
    List<Object> allsubtasks = new ArrayList<Object>();
    List<Object[]> taskRelation =taskRelationRepository.GetSubtasksRelations(id);
    for(Object[] subtasks:taskRelation){
        String tableName = (String) subtasks[0]; // Adjust types as per your database schema
        UUID[] childrenIdArray = (UUID[]) subtasks[1]; // Assuming children_id is UUID[] in database

        // Convert UUID[] to List<UUID>
        List<UUID> childrenId = Arrays.asList(childrenIdArray);
        switch (tableName){
            case "appointment":
                List<Appointment> appointments =appointmentRepository.findAllById(childrenId);
                allsubtasks.addAll(appointments);
                break;
            case "call":
                List<Call> calls =callRepository.findAllById(childrenId);
                allsubtasks.addAll(calls);
                break;
            case "contact":
                List<Contact> contacts =contactRepository.findAllById(childrenId);
                allsubtasks.addAll(contacts);
                break;
            case "event":
                List<Event> events =eventRepository.findAllById(childrenId);
                allsubtasks.addAll(events);
                break;
            case "mail":
                List<Mail> mails =mailRepository.findAllById(childrenId);
                allsubtasks.addAll(mails);
                break;
            case "payment":
                List<Payment> payments =paymentRepository.findAllById(childrenId);
                allsubtasks.addAll(payments);
                break;
            case "todo":
                List<ToDo> todos =toDoRepository.findAllById(childrenId);
                allsubtasks.addAll(todos);
                break;
        }

    }

    return ResponseEntity.ok(allsubtasks);
}
}