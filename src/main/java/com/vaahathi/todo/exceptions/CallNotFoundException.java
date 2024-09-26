package com.vaahathi.todo.exceptions;

public class CallNotFoundException extends RuntimeException {
    public CallNotFoundException(String message) {

        super("call is not found by this id");
    }
}
