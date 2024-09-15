package com.vaahathi.todo.service;

import com.vaahathi.todo.entity.Event;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.models.event.EventRequest;
import com.vaahathi.todo.models.event.EventResponse;
import com.vaahathi.todo.repository.EventRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    TaskRelationRepository taskRelationRepository;

    @Transactional
    public ResponseEntity<EventResponse> CreateEventAndUpdateTaskRel(@RequestBody EventRequest eventRequest) throws Exception {
        if (eventRequest.getPid() == null) {
            Event event = modelMapper.map(eventRequest, Event.class);
            List<String> tempHierarcy = new ArrayList<String>();
            tempHierarcy.add(eventRequest.getPurpose());
            event.setHierarchy(tempHierarcy);
            event.setParent(true);
            Event savedEvent = eventRepository.save(event);
            TaskRelation currentTaskRelation = new TaskRelation();
            currentTaskRelation.setId(savedEvent.getId());
            currentTaskRelation.setCid(new ArrayList<UUID>());
            currentTaskRelation.setRef("event");
            taskRelationRepository.save(currentTaskRelation);
            EventResponse eventResponse = modelMapper.map(savedEvent, EventResponse.class);
            return ResponseEntity.ok(eventResponse);
        } else {
            Event childEvent = modelMapper.map(eventRequest, Event.class);

            List<String> tempHierarcy = eventRequest.getParentHierarcy();
            tempHierarcy.add(childEvent.getPurpose());
            childEvent.setHierarchy(tempHierarcy);
            Event savedChildEvent = eventRepository.save(childEvent);

            TaskRelation taskRelation = new TaskRelation();
            taskRelation.setId(savedChildEvent.getId());
            taskRelation.setPid(eventRequest.getPid());
            taskRelation.setCid(new ArrayList<UUID>());
            taskRelation.setRef("event");
            taskRelationRepository.save(taskRelation);

            TaskRelation parentTaskRelation = taskRelationRepository.findById(eventRequest.getPid())
                    .orElseThrow(() -> new Exception("Parent task relation not found"));

            if (parentTaskRelation.getCid() == null) {
                parentTaskRelation.setCid(new ArrayList<>());
            } else {
                List<UUID> tempCid = parentTaskRelation.getCid();
                tempCid.add(savedChildEvent.getId());
                parentTaskRelation.setCid(tempCid);
            }
            taskRelationRepository.save(parentTaskRelation);

            EventResponse eventResponse = modelMapper.map(savedChildEvent, EventResponse.class);
            return ResponseEntity.ok(eventResponse);
        }
    }
}