package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.entity.ToDo;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.repository.CallRepository;
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
@RequestMapping("/api/calls")
public class CallControllers {

        @Autowired
        private CallRepository callRepository;
        @Operation(
                summary =  "create a new call",
                description = "Id will be automatically generated in UUID format."
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Call created successfully",
                        content = @Content(schema = @Schema(implementation = Call.class))),
                @ApiResponse(responseCode = "404", description = "Resource not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")})


        @PostMapping
        public Call createCall(@RequestBody Call call) {
            return callRepository.save(call);
        }
    @GetMapping("/list")
    public List<Call> getCallList(
            @RequestParam("category") String category,
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("taskType") String taskType) {
        return callRepository.findByCategoryAndOwnerIdAndTaskType(category, ownerId, taskType);
    }
    @PutMapping("/{id}")
    public Call updateCall(@PathVariable UUID id, @RequestBody Call updatedCall) {
        Call existingCall = callRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Call not found with "+id));
        existingCall.setTaskScheduled(updatedCall.isTaskScheduled());
        existingCall.setDueDate(updatedCall.getDueDate());
        existingCall.setUrgent(updatedCall.isUrgent());
        existingCall.setImportant(updatedCall.isImportant());
        existingCall.setPurpose(updatedCall.getPurpose());
        existingCall.setDependency(updatedCall.isDependency());
        existingCall.setOwnerId(updatedCall.getOwnerId());
        existingCall.setAssignedTo(updatedCall.getAssignedTo());
        existingCall.setPersonName(updatedCall.getPersonName());
        existingCall.setPhoneNumber(updatedCall.getPhoneNumber());
        existingCall.setCallNote(updatedCall.getCallNote());
        return callRepository.save(existingCall);
    }
}





