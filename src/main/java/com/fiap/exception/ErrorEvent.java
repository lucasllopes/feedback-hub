package com.fiap.exception;

public class ErrorEvent extends RuntimeException{

    public ErrorEvent(String message) {
        super(message);
    }
}
