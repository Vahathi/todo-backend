package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.ToDo;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.repository.AppointmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/appointment")
@Tag(name = "appointments", description = "Appointment maanagement APIs")
public class AppointmentControllers {
@Autowired
    AppointmentRepository appointmentRepository;
@Operation(
        summary = "creating an appointment",
        description = "Id will be automatically generated in UUID format"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appointment created successfully",
                content = @Content(schema = @Schema(implementation = Appointment.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})


@PostMapping ("/api/appointments")
public Appointment createAppointment(@RequestBody Appointment appointment) {
    return appointmentRepository.save(appointment);
}
@GetMapping
public List<Appointment> getAppointments(
        @RequestParam("category") String category,
        @RequestParam("ownerId") UUID ownerId,
        @RequestParam("taskType") String taskType) {

    return appointmentRepository.findByCategoryAndOwnerIdAndTaskType(category, ownerId, taskType);
}
    @PutMapping("/api/appointments/{id}")
    public Appointment updateAppointment(@PathVariable UUID id, @RequestBody Appointment updatedAppointment) {
        Appointment existingAppointment = (Appointment) appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment not found with id: " + id));
        existingAppointment.setTaskType(updatedAppointment.getTaskType());
        existingAppointment.setTaskScheduled(updatedAppointment.isTaskScheduled());
        existingAppointment.setDueDate(updatedAppointment.getDueDate());
        existingAppointment.setUrgent(updatedAppointment.isUrgent());
        existingAppointment.setImportant(updatedAppointment.isImportant());
        existingAppointment.setPurpose(updatedAppointment.getPurpose());
        existingAppointment.setDependency(updatedAppointment.isDependency());
        existingAppointment.setPersonName(updatedAppointment.getPersonName());
        existingAppointment.setPhoneNumber(updatedAppointment.getPhoneNumber());
        return appointmentRepository.save(existingAppointment);
    }
}
