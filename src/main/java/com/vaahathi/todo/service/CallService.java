package com.vaahathi.todo.service;

import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.entity.Status;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.call.CallRequest;
import com.vaahathi.todo.models.call.CallResponse;
import com.vaahathi.todo.repository.CallRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CallService {
    @Autowired
    TaskRelationRepository taskRelationRepository;

    @Autowired
    CallRepository callRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public CallResponse CreateCallAndUpdateTaskRel(CallRequest callRequest) throws Exception {
        if (callRequest.getPid() == null) {
            throw new Exception("PID cannot be empty");
        } else {
            Call call = modelMapper.map(callRequest, Call.class);
            List<String> tempHierarcy = callRequest.getParentHierarcy();
            tempHierarcy.add(call.getPurpose());
            call.setHierarchy(tempHierarcy);
            Call savedCall = callRepository.save(call);
            // adding a record in task relation table
            TaskRelation taskRelation = new TaskRelation();
            taskRelation.setId(savedCall.getId());
            taskRelation.setPid(callRequest.getPid());
            taskRelation.setCid(new ArrayList<UUID>());
            taskRelation.setRef("call");
            taskRelationRepository.save(taskRelation);
            // updating parent record in task relation table.
            if (taskRelationRepository.existsById(callRequest.getPid())) {
                TaskRelation parentRelation = taskRelationRepository.findById(callRequest.getPid()).orElseThrow(() -> new Exception("can't find parent with given pid"));
                parentRelation.getCid().add(savedCall.getId());
                taskRelationRepository.save(parentRelation);
            } else {
                throw new Exception("cant find parent with pid");
            }
            return modelMapper.map(savedCall, CallResponse.class);
        }
    }

    public Call closeCall(UUID callId) {
        Call call = callRepository.findById(callId).orElseThrow(() -> new ResourceNotFoundException("Call not found"));
        call.setStatus(Status.CLOSED);
        return callRepository.save(call);
    }

    public List<CallResponse> getCallsSortedByPriority() {
        List<Call> calls = callRepository.findAll();

        // Sort in memory using Comparator
        List<Call> sortedCalls = calls.stream()
                .sorted((c1, c2) -> {
                    if (c1.isImportant() && c1.isUrgent()) {
                        return -1;
                    } else if (!c1.isImportant() && c1.isUrgent()) {
                        return c2.isImportant() && c2.isUrgent() ? 1 : -1;
                    } else if (c1.isImportant() && !c1.isUrgent()) {
                        return c2.isImportant() && c2.isUrgent() || (!c2.isImportant() && c2.isUrgent()) ? 1 : -1;
                    } else {
                        return 1;  // Lowest priority
                    }
                })
                .collect(Collectors.toList());

        // Map to CallResponse and return
        return sortedCalls.stream()
                .map(call -> modelMapper.map(call, CallResponse.class))
                .collect(Collectors.toList());
    }
}


