package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Mail;
import com.vaahathi.todo.repository.MailRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mail")
public class MailControllers {
    @Autowired
    private MailRepository mailRepository;
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
    public Mail createMail(@RequestBody Mail mail) {
        mail.setPersonName(mail.getPersonName());
        mail.setId(mail.getId());
        mail.setPurpose(mail.getPurpose());
        mail.setDueDate(mail.getDueDate());
        mail.setImportant(!mail.isImportant());
        Mail savedMail = mailRepository.save(mail);
        return savedMail;
    }
    @GetMapping("list")
    public List<Mail> getMails(@RequestParam("category") String category,
                               @RequestParam("ownerId") UUID ownerId,
                               @RequestParam("taskType") String taskType) {
        return mailRepository.findByCategoryAndOwnerIdAndTaskType(category, ownerId, taskType);
    }
}
