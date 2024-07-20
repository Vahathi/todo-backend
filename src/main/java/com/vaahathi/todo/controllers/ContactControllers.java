package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.appointment.AppointmentRequest;
import com.vaahathi.todo.models.appointment.AppointmentResponse;
import com.vaahathi.todo.models.contact.ContactRequest;
import com.vaahathi.todo.models.contact.ContactResponse;
import com.vaahathi.todo.repository.ContactRepository;
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
@RequestMapping("/contacts")
public class ContactControllers {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Operation(
            summary = "Create a new Contact",
            description = "Id will be automatically generated in UUID format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "contact created successfully",
                    content = @Content(schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})

    @PostMapping("/create")
    public ContactResponse createContact(@RequestBody ContactRequest contactRequest) {
        contactRequest.setPersonName(contactRequest.getPersonName());
        contactRequest.setPhoneNumber(contactRequest.getPhoneNumber());
        Contact contact = modelMapper.map(contactRequest, Contact.class);
        return modelMapper.map(contact, ContactResponse.class);
    }
    @GetMapping("/List")
    public ResponseEntity<List<ContactResponse>> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        Type listType = new TypeToken<List<ContactResponse>>() {}.getType();
        List<ContactResponse> contactResponses = modelMapper.map(contacts, listType);
        return ResponseEntity.ok(contactResponses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable UUID id, @RequestBody ContactRequest updatedContactRequest) {
        Contact existingContact =  contactRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment not found with id: " + id));
        existingContact.setTaskScheduled(updatedContactRequest.isTaskScheduled());
        existingContact.setUrgent(updatedContactRequest.isUrgent());
        existingContact.setImportant(updatedContactRequest.isImportant());
        existingContact.setPurpose(updatedContactRequest.getPurpose());
        existingContact.setDependency(updatedContactRequest.isDependency());
        existingContact.setPersonName(updatedContactRequest.getPersonName());
        existingContact.setPhoneNumber(updatedContactRequest.getPhoneNumber());
        existingContact.setCid(updatedContactRequest.getCid());
        existingContact.setPid(updatedContactRequest.getPid());
        Contact updatedContact = contactRepository.save(existingContact);
        ContactResponse contactResponse = modelMapper.map(updatedContact, ContactResponse.class);
        return ResponseEntity.ok(contactResponse);
    }
}

