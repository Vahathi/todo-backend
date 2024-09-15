package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Mail;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.mail.MailRequest;
import com.vaahathi.todo.models.mail.MailResponse;
import com.vaahathi.todo.repository.MailRepository;
import com.vaahathi.todo.service.MailService;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/mail")
public class MailControllers {
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "Create a new Mail",
            description = "Id will be automatically generated in UUID format."
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mail created successfully",
                    content = @Content(schema = @Schema(implementation = Mail.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})


    @PostMapping("/create")
    public ResponseEntity<MailResponse> createMail(@RequestBody MailRequest mailRequest) throws Exception {
        MailResponse mailResponse = mailService.CreateMailAndUpdateTaskRel(mailRequest);
        return ResponseEntity.ok(mailResponse);
    }

    //    public ResponseEntity<MailResponse> createMail(@RequestBody MailRequest mailRequest) {
//        mailRequest.setPersonName(mailRequest.getPersonName());
//        mailRequest.setPurpose(mailRequest.getPurpose());
//        mailRequest.setImportant(!mailRequest.isImportant());
//        Mail mail = modelMapper.map(mailRequest, Mail.class);
//        Mail savedMail = mailRepository.save(mail);
//        MailResponse mailResponse = modelMapper.map(savedMail, MailResponse.class);
//        return ResponseEntity.ok(mailResponse);
    // }
    @GetMapping("/list")
    public ResponseEntity<List<MailResponse>> getMails(
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("category") String category) {
        List<Mail> mails = mailRepository.findByOwnerIdAndCategory(ownerId, category);
        mails.sort(Comparator.comparingInt(mail -> calculatePriority(mail.isImportant(), mail.isUrgent())));
        Type listType = new TypeToken<List<MailResponse>>() {
        }.getType();
        List<MailResponse> mailResponses = modelMapper.map(mails, listType);
        return ResponseEntity.ok(mailResponses);
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
    public ResponseEntity<Mail> getMailById(@PathVariable UUID id) {
        Optional<Mail> mail = mailRepository.findById(id);
        if (mail.isPresent()) {
            return ResponseEntity.ok(mail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MailResponse> updateMail(@PathVariable UUID id, @RequestBody MailRequest updatedMailRequest) {
        Mail existingMail = mailRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("mail not found with id: " + id));
        modelMapper.map(updatedMailRequest, existingMail);
        Mail updatedMail = mailRepository.save(existingMail);
        MailResponse callResponse = modelMapper.map(updatedMail, MailResponse.class);
        return ResponseEntity.ok(callResponse);
    }
}

