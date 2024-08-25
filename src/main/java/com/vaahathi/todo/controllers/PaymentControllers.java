package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Payment;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) throws Exception {
        PaymentResponse paymentResponse = paymentService.createPaymentTaskRel(paymentRequest);
        return ResponseEntity.ok(paymentResponse);
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
        Payment existingPayment=  paymentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment not found with id: " + id));
        existingPayment.setTaskType(updatedPaymentRequest.getTaskType());
        existingPayment.setTaskScheduled(updatedPaymentRequest.isTaskScheduled());
        existingPayment.setUrgent(updatedPaymentRequest.isUrgent());
        existingPayment.setImportant(updatedPaymentRequest.isImportant());
        existingPayment.setPurpose(updatedPaymentRequest.getPurpose());
        existingPayment.setDependency(updatedPaymentRequest.isDependency());
        existingPayment.setCid(updatedPaymentRequest.getCid());
        existingPayment.setPid(updatedPaymentRequest.getPid());
        existingPayment.setMessage(updatedPaymentRequest.getMessage());
        //  modelMapper.map(updatedMailRequest, existingMail);

        Payment updatedPayment = paymentRepository.save(existingPayment);
        PaymentResponse paymentResponse = modelMapper.map(updatedPayment, PaymentResponse.class);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/List")
    public ResponseEntity<List<PaymentResponse>>getPayments
            (@RequestParam("ownerId") UUID ownerId,
             @RequestParam("taskType") String taskType,
             @RequestParam("category") String category) {
        List<Payment> payments = paymentRepository.findByOwnerIdAndTaskTypeAndCategory(ownerId, taskType, category);
        Type listType = new TypeToken<List<PaymentResponse>>() {}.getType();
        List<PaymentResponse> paymentResponses = modelMapper.map(payments, listType);
        return ResponseEntity.ok(paymentResponses);
    }
    }
