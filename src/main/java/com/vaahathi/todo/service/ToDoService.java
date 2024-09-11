package com.vaahathi.todo.service;

import com.vaahathi.todo.entity.Status;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.entity.ToDo;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.todo.ToDoRequest;
import com.vaahathi.todo.models.todo.ToDoResponse;
import com.vaahathi.todo.repository.TaskRelationRepository;
import com.vaahathi.todo.repository.ToDoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {
    @Autowired
    TaskRelationRepository taskRelationRepository;

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ToDoResponse createToDoAndUpdateTaskRel(ToDoRequest toDoRequest) throws Exception {
        if (toDoRequest.getPid() == null) {
            throw new Exception("PID cannot be empty");
        } else {
            ToDo toDo = modelMapper.map(toDoRequest, ToDo.class);
            List<String> tempHierarcy = toDoRequest.getParentHierarcy();
            tempHierarcy.add(toDo.getPurpose());
            toDo.setHierarchy(tempHierarcy);
            ToDo savedToDo = toDoRepository.save(toDo);
            // adding a record in task relation table
            TaskRelation taskRelation = new TaskRelation();
            taskRelation.setId(savedToDo.getId());
            taskRelation.setPid(toDoRequest.getPid());
            taskRelation.setCid(new ArrayList<UUID>());
            taskRelation.setRef("todo");
            taskRelationRepository.save(taskRelation);
            // updating parent record in task relation table.
            if (taskRelationRepository.existsById(toDoRequest.getPid())) {
                TaskRelation parentRelation = taskRelationRepository.findById(toDoRequest.getPid()).orElseThrow(() -> new Exception("can't find parent with given pid"));
                parentRelation.getCid().add(savedToDo.getId());
                taskRelationRepository.save(parentRelation);
            } else {
                throw new Exception("cant find parent with pid");
            }

            return modelMapper.map(savedToDo, ToDoResponse.class);
        }
    }

    public ToDo closeToDo(UUID toDoId) {
        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow(() -> new ResourceNotFoundException("ToDo not found"));
        toDo.setStatus(Status.CLOSED);
        return toDoRepository.save(toDo);
    }
}
