package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Event;
import com.vaahathi.todo.repository.EventRepository;
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
@RequestMapping("/events")
public class EventControllers {
    @Autowired
    EventRepository eventRepository;
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
    public Event createEvent(@RequestBody Event event) {
        event.setName(event.getName());
        event.setPurpose(event.getPurpose());

        // Save the event to the database
        Event savedEvent = eventRepository.save(event);

        return savedEvent;
    }
    @GetMapping("/list")
    public List<Event> getEvents(
            @RequestParam("category") String category,
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("isParent") boolean isParent) {
        return eventRepository.findByCategoryAndOwnerIdAndIsParent(category, ownerId, isParent);

    }


}
