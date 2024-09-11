package com.vaahathi.todo.service;


import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.Status;
import com.vaahathi.todo.entity.TaskRelation;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.appointment.AppointmentRequest;
import com.vaahathi.todo.models.appointment.AppointmentResponse;
import com.vaahathi.todo.repository.AppointmentRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    @Autowired
    TaskRelationRepository taskRelationRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public AppointmentResponse CreateAppointmentAndUpdateTaskRel(AppointmentRequest appointmentRequest) throws Exception {
        if (appointmentRequest.getPid() == null) {
            throw new Exception("PID cannot be empty");
        } else {
            Appointment appointment = modelMapper.map(appointmentRequest, Appointment.class);
            List<String> tempHierarcy = appointmentRequest.getParentHierarcy();
            tempHierarcy.add(appointment.getPurpose());
            appointment.setHierarchy(tempHierarcy);
            Appointment savedAppointment = appointmentRepository.save(appointment);
            // adding a record in task relation table
            TaskRelation taskRelation = new TaskRelation();
            taskRelation.setId(savedAppointment.getId());
            taskRelation.setPid(appointmentRequest.getPid());
            taskRelation.setCid(new ArrayList<UUID>());
            taskRelation.setRef("appointment");
            taskRelationRepository.save(taskRelation);
            // updating parent record in task relation table.
            if (taskRelationRepository.existsById(appointmentRequest.getPid())) {
                TaskRelation parentRelation = taskRelationRepository.findById(appointmentRequest.getPid()).orElseThrow(() -> new Exception("can't find parent with given pid"));
                parentRelation.getCid().add(savedAppointment.getId());
                taskRelationRepository.save(parentRelation);
            } else {
                throw new Exception("cant find parent with pid");
            }
            return modelMapper.map(savedAppointment, AppointmentResponse.class);
        }
    }

    public Appointment closeAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointment.setStatus(Status.CLOSED);
        return appointmentRepository.save(appointment);
    }
}
