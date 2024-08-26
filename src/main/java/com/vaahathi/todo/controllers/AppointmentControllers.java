package com.vaahathi.todo.controllers;

import com.vaahathi.todo.entity.Appointment;
import com.vaahathi.todo.exceptions.ResourceNotFoundException;
import com.vaahathi.todo.models.appointment.AppointmentRequest;
import com.vaahathi.todo.models.appointment.AppointmentResponse;
import com.vaahathi.todo.repository.AppointmentRepository;
import com.vaahathi.todo.repository.TaskRelationRepository;
import com.vaahathi.todo.service.AppointmentService;
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
    AppointmentService appointmentService;
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    TaskRelationRepository taskRelationRepository;
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


@PostMapping ("/create")
public ResponseEntity<AppointmentResponse>  createAppointment(@RequestBody AppointmentRequest appointmentRequest) throws Exception {
    AppointmentResponse appointmentResponse = appointmentService.CreateAppointmentAndUpdateTaskRel(appointmentRequest);
    return ResponseEntity.ok(appointmentResponse);
}
//public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
//    Appointment appointment= modelMapper.map(appointmentRequest, Appointment.class);
//    Appointment savedAppointment = appointmentRepository.save(appointment);
//    AppointmentResponse appointmentResponse = modelMapper.map(savedAppointment, AppointmentResponse.class);
//    return ResponseEntity.ok(appointmentResponse);
//}
@GetMapping ("/List")
public ResponseEntity <List<AppointmentResponse>> getAppointments(
        @RequestParam("ownerId") UUID ownerId,
        @RequestParam("category") String category) {
    List<Appointment> appointments = appointmentRepository.findByOwnerIdAndTaskTypeAndCategory(ownerId , "appointment" ,category);
    Type listType = new TypeToken<List<AppointmentResponse>>() {}.getType();
    List<AppointmentResponse> appointmentResponses = modelMapper.map(appointments, listType);
    return ResponseEntity.ok(appointmentResponses);
}
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable UUID id, @RequestBody AppointmentRequest updatedAppointmentRequest) {
        Appointment existingAppointment =  appointmentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment not found with id: " + id));
        modelMapper.map(updatedAppointmentRequest, existingAppointment);
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        AppointmentResponse appointmentResponse = modelMapper.map(updatedAppointment, AppointmentResponse.class);
        return ResponseEntity.ok(appointmentResponse);
    }
}

