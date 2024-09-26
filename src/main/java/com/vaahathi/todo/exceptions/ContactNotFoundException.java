package com.vaahathi.todo.exceptions;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {

        super("contact not found with the id");
    }
}
