package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Payments;
import com.vaahathi.todo.repository.PaymentsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentControllers {
    @Autowired
    PaymentsRepository paymentsRepository;

    @Operation(
            summary = "process a new payment",
            description = "Id will be automatically generated in UUID format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "payment processed successfully",
                    content = @Content(schema = @Schema(implementation = Payments.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/process")
    public String processPayment(@RequestBody Payments payments) {
        return "Payment processed successfully";
    }

    @PutMapping("/process")
    public Payments updatePayment(@RequestBody Payments payments) {
        Payments response = new Payments();
        response.setDueDate("15-09-26");
        return response;
    }
    @GetMapping("/paymentList")
    public List<String> getPaymentList(@RequestParam String ownerId, @RequestParam String category, @RequestParam String taskType) {
        List<String> paymentList = Arrays.asList("Payment1", "Payment2", "Payment3");
        return paymentList;
    }
}