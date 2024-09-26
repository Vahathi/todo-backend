package com.vaahathi.todo.exceptions;

public class ScheduledTaskNotFoundException extends RuntimeException {
    public ScheduledTaskNotFoundException(String message) {

        super("provided scheduled date is mis matched");
    }
}
