package com.example.booking_service.exception;

public class AlreadyReservedDatesException extends RuntimeException {

    public AlreadyReservedDatesException(String message) {
        super(message);
    }

}
