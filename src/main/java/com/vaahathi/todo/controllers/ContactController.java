package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.call.CallRequest;
import com.vaahathi.todo.models.call.CallResponse;
import com.vaahathi.todo.models.contact.ContactRequest;
import com.vaahathi.todo.models.contact.ContactResponse;
import com.vaahathi.todo.models.contact.SearchResponse;
import com.vaahathi.todo.repository.ContactRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contacts")
public class ContactController {
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
    public ResponseEntity <ContactResponse> createContact(@RequestBody ContactRequest contactRequest) {
        Contact contact = modelMapper.map(contactRequest, Contact.class);
        Contact savedContact= contactRepository.save(contact);
        ContactResponse contactResponse = modelMapper.map(savedContact, ContactResponse.class);
        return ResponseEntity.ok(contactResponse);
    }
    @GetMapping("/List")
    public ResponseEntity <List<ContactResponse>> getContacts(
            @RequestParam("ownerId")  UUID ownerId,
            @RequestParam("category") String category) {
        List<Contact> contacts = contactRepository.findByOwnerIdAndTaskTypeAndCategory(ownerId,"contact",category);
        Type listType = new TypeToken<List<ContactResponse>>() {}.getType();
        List<ContactResponse> contactResponses = modelMapper.map(contacts, listType);
        return ResponseEntity.ok(contactResponses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable UUID id, @RequestBody ContactRequest updatedContactRequest) {
        Contact existingContact =  contactRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Contact not found with id: " + id));
        modelMapper.map(updatedContactRequest, existingContact);

        Contact updatedContact = contactRepository.save(existingContact);

        ContactResponse contactResponse = modelMapper.map(updatedContact, ContactResponse.class);

        return ResponseEntity.ok(contactResponse);
    }
    @GetMapping("/search")
    public ResponseEntity<List<SearchResponse>> searchContact(@RequestParam String search,@RequestParam UUID ownerId) {
        List<Contact> existingContact =  contactRepository.searchByOwnerIDAndName(ownerId,search);
        List<SearchResponse> destinationList = existingContact.stream()
                .map(source -> modelMapper.map(source, SearchResponse.class))
                .toList();

        return ResponseEntity.ok(destinationList);
    }
    }