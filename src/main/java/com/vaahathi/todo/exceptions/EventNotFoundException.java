package com.vaahathi.todo.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {

        super("event not found by this id");
    }
}
