package com.vaahathi.todo.exceptions;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super("Appointment is not found by this id");
    }
}
