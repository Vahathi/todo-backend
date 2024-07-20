package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Payment;
import com.vaahathi.todo.models.payment.PaymentRequest;
import com.vaahathi.todo.models.payment.PaymentResponse;
import com.vaahathi.todo.repository.PaymentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentControllers {
    @Autowired
    PaymentRepository paymentsRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "process a new payment",
            description = "Id will be automatically generated in UUID format."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "payment processed successfully",
                    content = @Content(schema = @Schema(implementation = Payment.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        boolean paymentSuccessful = processPaymentLogic(paymentRequest);
        PaymentResponse paymentResponse;
        if (paymentSuccessful) {
            paymentResponse = new PaymentResponse("payment processed successfully");
        } else {
            paymentResponse = new PaymentResponse("payment processing failed");
        }
        return ResponseEntity.ok(paymentResponse);
    }
    private boolean processPaymentLogic(PaymentRequest paymentRequest) {
        return true;
    }

    @PutMapping("/id")
    public ResponseEntity<PaymentResponse>  updatePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = modelMapper.map(paymentRequest, PaymentResponse.class);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/paymentList")
    public ResponseEntity <PaymentResponse> getPaymentList(@RequestParam UUID ownerId,
                                          @RequestParam String category,
                                          @RequestParam String taskType) {
        List<String> paymentList = Arrays.asList("Payment1", "Payment2", "Payment3");
        PaymentResponse response = modelMapper.map(paymentList, PaymentResponse.class);
        return ResponseEntity.ok(response);
    }
}