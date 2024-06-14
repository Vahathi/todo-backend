package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Contact;
import com.vaahathi.todo.repository.ContactRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactControllers {
    @Autowired
    ContactRepository contactRepository;
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
    public Contact createContact(@RequestBody Contact contact) {
        contact.setId(contact.getId());
        contact.setName(contact.getName());
        contact.setPhone(contact.getPhone());
        return contact;
    }
}
