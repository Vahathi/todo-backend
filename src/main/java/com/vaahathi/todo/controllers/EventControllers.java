package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Event;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.dto.ErrorResponse;
import com.vaahathi.todo.models.event.EventRequest;
import com.vaahathi.todo.models.event.EventResponse;
import com.vaahathi.todo.repository.EventRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import com.vaahathi.todo.service.EventService;
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
@RequestMapping("/events")
public class EventControllers {

    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;

    @Autowired
    TaskRelationRepository taskRelationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "Create a new Event",
            description = "Id will be automatically generated in UUID format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully",
                    content = @Content(schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest) {
        try {
            // Directly return the ResponseEntity from the service
            return eventService.CreateEventAndUpdateTaskRel(eventRequest);
        } catch (Exception ex) {
            // Log the exception and prepare error response
            List<String> stackTrace = Arrays.stream(ex.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.toList());

            // Create the error response
            ErrorResponse errorResponse = new ErrorResponse(
                    "An error occurred while creating the event.",
                    ex.getMessage(),
                    stackTrace
            );

            // Return the error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventResponse>> getEvents(
            @RequestParam("category") String category,
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("isParent") boolean isParent) {
        List<Event> events = eventRepository.findByCategoryAndOwnerIdAndIsParent(category, ownerId, isParent);
        events.sort(Comparator.comparingInt(event -> calculatePriority(event.isImportant(), event.isUrgent())));
        Type listType = new TypeToken<List<EventResponse>>() {
        }.getType();
        List<EventResponse> eventResponses = modelMapper.map(events, listType);
        return ResponseEntity.ok(eventResponses);
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
    public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable UUID id, @RequestBody EventRequest updatedEventRequest) {
        Event existingEvent = (Event) eventRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Event not found with id: " + id));
        modelMapper.map(updatedEventRequest, existingEvent);
        Event updatedEvent = eventRepository.save(existingEvent);
        EventResponse eventResponse = modelMapper.map(updatedEvent, EventResponse.class);
        return ResponseEntity.ok(eventResponse);
    }
}






























