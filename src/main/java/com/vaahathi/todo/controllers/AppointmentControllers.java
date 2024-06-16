package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.entity.Call;
import com.vaahathi.todo.entity.ToDo;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.appointment.AppointmentRequest;
import com.vaahathi.todo.models.appointment.AppointmentResponse;
import com.vaahathi.todo.repository.AppointmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/appointment")
@Tag(name = "appointments", description = "Appointment maanagement APIs")
public class AppointmentControllers {
@Autowired
    AppointmentRepository appointmentRepository;
@Autowired
private ModelMapper modelMapper;

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
public ResponseEntity<AppointmentResponse>  createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
    Appointment appointment = modelMapper.map(appointmentRequest, Appointment.class);
    Appointment savedAppointment = appointmentRepository.save(appointment);
    AppointmentResponse appointmentResponse = modelMapper.map(savedAppointment, AppointmentResponse.class);
    return ResponseEntity.ok(appointmentResponse);
}
@GetMapping
public ResponseEntity <List<AppointmentResponse>> getAppointments(
        @RequestParam("category") String category,
        @RequestParam("ownerId") UUID ownerId,
        @RequestParam("taskType") String taskType) {
    List<Appointment> appointments = appointmentRepository.findByCategoryAndOwnerIdAndTaskType(category, ownerId, taskType);
    Type listType = new TypeToken<List<AppointmentResponse>>() {}.getType();
    List<AppointmentResponse> appointmentResponses = modelMapper.map(appointments, listType);
    return ResponseEntity.ok(appointmentResponses);
}
    @PutMapping("/api/appointments/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable UUID id, @RequestBody AppointmentRequest updatedAppointmentRequest) {
        Appointment existingAppointment =  appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment not found with id: " + id));
        existingAppointment.setTaskType(updatedAppointmentRequest.getTaskType());
        existingAppointment.setTaskScheduled(updatedAppointmentRequest.isTaskScheduled());
        existingAppointment.setDueDate(updatedAppointmentRequest.getDueDate());
        existingAppointment.setUrgent(updatedAppointmentRequest.isUrgent());
        existingAppointment.setImportant(updatedAppointmentRequest.isImportant());
        existingAppointment.setPurpose(updatedAppointmentRequest.getPurpose());
        existingAppointment.setDependency(updatedAppointmentRequest.isDependency());
        existingAppointment.setPersonName(updatedAppointmentRequest.getPersonName());
        existingAppointment.setPhoneNumber(updatedAppointmentRequest.getPhoneNumber());
        modelMapper.map(updatedAppointmentRequest, existingAppointment);

        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        AppointmentResponse appointmentResponse = modelMapper.map(updatedAppointment, AppointmentResponse.class);
        return ResponseEntity.ok(appointmentResponse);
    }
}
