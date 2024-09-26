package com.vaahathi.todo.exceptions;

public class OwnerIdNotFoundException extends RuntimeException {
    public OwnerIdNotFoundException(String message) {
        super("owner id mis matched");
    }
}
