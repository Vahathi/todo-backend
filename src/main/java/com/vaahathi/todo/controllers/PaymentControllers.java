package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Payment;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.dto.ErrorResponse;
import com.vaahathi.todo.models.payment.PaymentRequest;
import com.vaahathi.todo.models.payment.PaymentResponse;
import com.vaahathi.todo.repository.PaymentRepository;
import com.vaahathi.todo.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentControllers {
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;
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
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Call the service to process the payment and create the payment task relationship
            PaymentResponse paymentResponse = paymentService.createPaymentTaskRel(paymentRequest);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception ex) {
            // Log the exception and capture the stack trace
            List<String> stackTrace = Arrays.stream(ex.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.toList());

            // Create a custom error response with a general error message and the stack trace
            ErrorResponse errorResponse = new ErrorResponse(
                    "An error occurred while processing the payment.",
                    ex.getMessage(),
                    stackTrace
            );

            // Return the error response with 500 Internal Server Error status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
//        paymentRequest.setPurpose(paymentRequest.getPurpose());
//        paymentRequest.setImportant(!paymentRequest.isImportant());
//        Payment payment = modelMapper.map(paymentRequest, Payment.class);
//        Payment savedPayment = paymentRepository.save(payment);
//        PaymentResponse paymentResponse = modelMapper.map(savedPayment, PaymentResponse.class);
//        return ResponseEntity.ok(paymentResponse);
//    }
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable UUID id, @RequestBody PaymentRequest updatedPaymentRequest) {
        Payment existingPayment = paymentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Payment not found with id: " + id));
        modelMapper.map(updatedPaymentRequest, existingPayment);
        Payment updatedPayment = paymentRepository.save(existingPayment);
        PaymentResponse paymentResponse = modelMapper.map(updatedPayment, PaymentResponse.class);
        return ResponseEntity.ok(paymentResponse);
    }


    @GetMapping("/list")
    public ResponseEntity<List<PaymentResponse>> getPayments(
            @RequestParam("ownerId") UUID ownerId,
            @RequestParam("category") String category) {
        List<Payment> payments = paymentRepository.findByOwnerIdAndCategory(ownerId, category);
        payments.sort(Comparator.comparingInt(payment -> calculatePriority(payment.isImportant(), payment.isUrgent())));
        Type listType = new TypeToken<List<PaymentResponse>>() {
        }.getType();
        List<PaymentResponse> paymentResponses = modelMapper.map(payments, listType);
        return ResponseEntity.ok(paymentResponses);
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
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
