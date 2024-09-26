package com.vaahathi.todo.exceptions;

public class ToDoNotFoundException extends RuntimeException {
    public ToDoNotFoundException(String message) {

        super("todo not found by this id");
    }
}
