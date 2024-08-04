package com.vaahathi.todo.service;

import com.vaahathi.todo.entity.Payment;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.models.mail.MailResponse;
import com.vaahathi.todo.models.payment.PaymentRequest;
import com.vaahathi.todo.models.payment.PaymentResponse;
import com.vaahathi.todo.repository.PaymentRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    TaskRelationRepository taskRelationRepository;

    @Autowired
    PaymentRepository paymentsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public PaymentResponse createPaymentTaskRel(PaymentRequest paymentRequest) throws Exception {
        if(paymentRequest.getPid() == null){
            throw new Exception("PID cannot be empty");
        }
        else {
            Payment payments = modelMapper.map(paymentRequest, Payment.class);
            Payment savedPayment = paymentsRepository.save(payments);
            // adding a record in task relation table
            TaskRelation taskRelation= new TaskRelation();
            taskRelation.setId(savedPayment.getId());
            taskRelation.setPid(paymentRequest.getPid());
            taskRelation.setCid(new ArrayList<UUID>());
            taskRelation.setRef("payment");
            taskRelationRepository.save(taskRelation);
            // updating parent record in task relation table.
            if (taskRelationRepository.existsById(paymentRequest.getPid())){
                TaskRelation parentRelation =taskRelationRepository.findById(paymentRequest.getPid()).orElseThrow(()-> new Exception("can't find parent with given pid"));
                parentRelation.getCid().add(savedPayment.getId());
                taskRelationRepository.save(parentRelation);
            }else{ throw new Exception("cant find parent with pid");}

            return modelMapper.map(savedPayment, PaymentResponse.class);
        }
    }
}
