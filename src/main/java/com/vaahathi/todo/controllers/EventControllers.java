package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Event;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.models.event.EventRequest;
import com.vaahathi.todo.models.event.EventResponse;
import com.vaahathi.todo.repository.EventRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventControllers {
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
    public ResponseEntity<EventResponse>  createEvent(@RequestBody EventRequest eventRequest) throws Exception {
        if (eventRequest.getPid() == null){
            eventRequest.setName(eventRequest.getName());
            eventRequest.setPurpose(eventRequest.getPurpose());
            Event event = modelMapper.map(eventRequest, Event.class);
            Event savedEvent = eventRepository.save(event);
            // add to task relation
            TaskRelation currentTaskRelation = new TaskRelation();
            currentTaskRelation.setId(savedEvent.getId());
            currentTaskRelation.setCid(new ArrayList<UUID>());
            currentTaskRelation.setRef("event");
            taskRelationRepository.save(currentTaskRelation);
            EventResponse eventResponse = modelMapper.map(savedEvent, EventResponse.class);
            return ResponseEntity.ok(eventResponse);
        }else {
            throw new Exception("Event cannot have pid");
        }

    }
    @GetMapping("/list")
    public ResponseEntity<List<EventResponse>> getEvents(
            @RequestParam("category") String category,
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("isParent") boolean isParent) {
        List<Event> events = eventRepository.findByCategoryAndOwnerIdAndIsParent(category, ownerId, isParent);
        Type listType = new TypeToken<List<EventResponse>>() {}.getType();
        List<EventResponse> eventResponses = modelMapper.map(events, listType);
        return ResponseEntity.ok(eventResponses);
    }


}
