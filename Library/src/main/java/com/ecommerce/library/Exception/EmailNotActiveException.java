package com.ecommerce.library.Exception;

public class EmailNotActiveException extends RuntimeException {
    public EmailNotActiveException(String message) {
        super(message);
    }
}
