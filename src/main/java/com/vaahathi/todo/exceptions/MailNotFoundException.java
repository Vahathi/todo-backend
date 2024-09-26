package com.vaahathi.todo.exceptions;

public class MailNotFoundException extends RuntimeException {
    public MailNotFoundException(String message) {

        super("mail not found by this id");
    }
}
