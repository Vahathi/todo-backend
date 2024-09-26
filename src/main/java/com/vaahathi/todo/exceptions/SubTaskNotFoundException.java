package com.vaahathi.todo.exceptions;

public class SubTaskNotFoundException extends RuntimeException {
    public SubTaskNotFoundException(String message) {

        super("no subtask found by the provided id");
    }
}
