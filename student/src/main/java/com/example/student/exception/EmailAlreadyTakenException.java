package com.example.student.exception;

public class EmailAlreadyTakenException extends  Exception{
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
