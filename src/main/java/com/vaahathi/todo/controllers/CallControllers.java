package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.call.CallRequest;
import com.vaahathi.todo.models.call.CallResponse;
import com.vaahathi.todo.models.dto.ErrorResponse;
import com.vaahathi.todo.repository.CallRepository;
import com.vaahathi.todo.service.CallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calls")
public class CallControllers {

    @Autowired
    private CallRepository callRepository;
    @Autowired
    private CallService callService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "create a new call",
            description = "Id will be automatically generated in UUID format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Call created successfully",
                    content = @Content(schema = @Schema(implementation = Call.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})


    @PostMapping("/create")
    public ResponseEntity<?> createCall(@RequestBody CallRequest callRequest) {
        try {
            CallResponse callResponse = callService.CreateCallAndUpdateTaskRel(callRequest);
            return ResponseEntity.ok(callResponse);
        } catch (Exception ex) {
            // Log the exception
            List<String> stackTrace = Arrays.stream(ex.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.toList());

            // Create error response with message and stack trace
            ErrorResponse errorResponse = new ErrorResponse(
                    "An error occurred while creating the call.",
                    ex.getMessage(),
                    stackTrace
            );

            // Return error response with stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //        public ResponseEntity <CallResponse> createCall(@RequestBody CallRequest callRequest) {
//            Call call = modelMapper.map(callRequest, Call.class);
//            Call savedCall = callRepository.save(call);
//            CallResponse callResponse = modelMapper.map(savedCall, CallResponse.class);
//            return ResponseEntity.ok(callResponse);
//        }
    @GetMapping("/list")
    public ResponseEntity<List<CallResponse>> getCallList(
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("category") String category) {
        List<Call> calls = callRepository.findByOwnerIdAndCategory(ownerId, category);
        calls.sort(Comparator.comparingInt(call -> calculatePriority(call.isImportant(), call.isUrgent())));
        Type listType = new TypeToken<List<CallResponse>>() {
        }.getType();
        List<CallResponse> callResponses = modelMapper.map(calls, listType);
        return ResponseEntity.ok(callResponses);
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

    @GetMapping("/{id}")
    public ResponseEntity<Call> getCallById(@PathVariable UUID id) {
        Optional<Call> call = callRepository.findById(id);
        if (call.isPresent()) {
            return ResponseEntity.ok(call.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CallResponse> updateCall(@PathVariable UUID id, @RequestBody CallRequest updatedCallRequest
    ) {
        Call existingCall = callRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Call not found with " + id));
        modelMapper.map(updatedCallRequest, existingCall);
        Call updatedCall = callRepository.save(existingCall);
        CallResponse callResponse = modelMapper.map(updatedCall, CallResponse.class);
        return ResponseEntity.ok(callResponse);
    }
}






