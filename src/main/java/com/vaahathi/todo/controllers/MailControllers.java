package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Mail;
import com.vaahathi.todo.models.mail.MailRequest;
import com.vaahathi.todo.models.mail.MailResponse;
import com.vaahathi.todo.repository.MailRepository;
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
@RequestMapping("/mail")
public class MailControllers {
    @Autowired
    private MailRepository mailRepository;
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
    public ResponseEntity<MailResponse> createMail(@RequestBody MailRequest mailRequest) {
        mailRequest.setPersonName(mailRequest.getPersonName());
        mailRequest.setPurpose(mailRequest.getPurpose());
        mailRequest.setDueDate(mailRequest.getDueDate());
        mailRequest.setImportant(!mailRequest.isImportant());
        Mail mail = modelMapper.map(mailRequest, Mail.class);
        Mail savedMail = mailRepository.save(mail);
        MailResponse mailResponse = modelMapper.map(savedMail, MailResponse.class);
        return ResponseEntity.ok(mailResponse);
    }
    @GetMapping("list")
    public ResponseEntity<List<MailResponse>>getMails(@RequestParam("category") String category,
                               @RequestParam("ownerId") UUID ownerId,
                               @RequestParam("taskType") String taskType) {
        List<Mail> mails = mailRepository.findByCategoryAndOwnerIdAndTaskType(category, ownerId, taskType);
        Type listType = new TypeToken<List<MailResponse>>() {}.getType();
        List<MailResponse> mailResponses = modelMapper.map(mails, listType);
        return ResponseEntity.ok(mailResponses);
    }
}
