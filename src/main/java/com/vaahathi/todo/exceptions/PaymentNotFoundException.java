package com.vaahathi.todo.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {

        super("payment not found by this id");
    }
}
