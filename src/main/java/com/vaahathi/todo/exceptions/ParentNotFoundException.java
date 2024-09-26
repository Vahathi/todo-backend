package com.vaahathi.todo.exceptions;

public class ParentNotFoundException extends RuntimeException {
    public ParentNotFoundException(String message) {

        super("parent has not there for this task");
    }
}
