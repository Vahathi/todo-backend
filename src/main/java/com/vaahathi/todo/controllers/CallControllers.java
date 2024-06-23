package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.entity.ToDo;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.call.CallRequest;
import com.vaahathi.todo.models.call.CallResponse;
import com.vaahathi.todo.repository.CallRepository;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/calls")
public class CallControllers {

        @Autowired
        private CallRepository callRepository;
        @Autowired
        private ModelMapper modelMapper;
        @Operation(
                summary =  "create a new call",
                description = "Id will be automatically generated in UUID format."
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Call created successfully",
                        content = @Content(schema = @Schema(implementation = Call.class))),
                @ApiResponse(responseCode = "404", description = "Resource not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")})


        @PostMapping ("/create")
        public ResponseEntity <CallResponse> createCall(@RequestBody CallRequest callRequest) {
            Call call = modelMapper.map(callRequest, Call.class);
            Call savedCall = callRepository.save(call);
            CallResponse callResponse = modelMapper.map(savedCall, CallResponse.class);
            return ResponseEntity.ok(callResponse);
        }
    @GetMapping("/list")
    public ResponseEntity<List<CallResponse> >getCallList(
            @RequestParam("category") String category,
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("taskType") String taskType) {
        List<Call> calls = callRepository.findByCategoryAndOwnerIdAndTaskType(category, ownerId, taskType);
        Type listType = new TypeToken<List<CallResponse>>() {}.getType();
        List<CallResponse> callResponses = modelMapper.map(calls, listType);
        return ResponseEntity.ok(callResponses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CallResponse> updateCall(@PathVariable UUID id, @RequestBody CallRequest updatedCallRequest
    ) {
        Call existingCall = callRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Call not found with "+id));
        existingCall.setTaskScheduled(updatedCallRequest.isTaskScheduled());
        existingCall.setDueDate(updatedCallRequest.getDueDate());
        existingCall.setUrgent(updatedCallRequest.isUrgent());
        existingCall.setImportant(updatedCallRequest.isImportant());
        existingCall.setPurpose(updatedCallRequest.getPurpose());
        existingCall.setDependency(updatedCallRequest.isDependency());
        existingCall.setAssignedTo(updatedCallRequest.getAssignedTo());
        existingCall.setPersonName(updatedCallRequest.getPersonName());
        existingCall.setPhoneNumber(updatedCallRequest.getPhoneNumber());
        existingCall.setCallNote(updatedCallRequest.getCallNote());
        modelMapper.map(updatedCallRequest, existingCall);

        Call updatedCall = callRepository.save(existingCall);
        CallResponse callResponse = modelMapper.map(updatedCall, CallResponse.class);
        return ResponseEntity.ok(callResponse);
    }
}





