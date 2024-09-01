package com.vaahathi.todo.service;

import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.models.call.CallRequest;
import com.vaahathi.todo.models.call.CallResponse;
import com.vaahathi.todo.models.contact.ContactRequest;
import com.vaahathi.todo.models.contact.ContactResponse;
import com.vaahathi.todo.repository.CallRepository;
import com.vaahathi.todo.repository.ContactRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.UUID;

public class ContactSerivce {
    @Autowired
    TaskRelationRepository taskRelationRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ContactResponse CreateContactAndUpdateTaskRel(ContactRequest contactRequest) throws Exception {
        if(contactRequest.getPid() == null){
            throw new Exception("PID cannot be empty");
        }
        else {
            Contact contact= modelMapper.map(contactRequest, Contact.class);
            Contact savedContact = contactRepository.save(contact);
            // adding a record in task relation table
            TaskRelation taskRelation= new TaskRelation();
            taskRelation.setId(savedContact.getId());
            taskRelation.setPid(contactRequest.getPid());
            taskRelation.setCid(new ArrayList<UUID>());
            taskRelation.setRef("contact");
            taskRelationRepository.save(taskRelation);
            // updating parent record in task relation table.
            if (taskRelationRepository.existsById(contactRequest.getPid())){
                TaskRelation parentRelation =taskRelationRepository.findById(contactRequest.getPid()).orElseThrow(()-> new Exception("can't find parent with given pid"));
                parentRelation.getCid().add(savedContact.getId());
                taskRelationRepository.save(parentRelation);
            }else{ throw new Exception("cant find parent with pid");}

            return modelMapper.map(savedContact, ContactResponse.class);
        }
    }
}
