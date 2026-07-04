package com.parking.parking_slot_booking_system.exception;



public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email){
        super("Email already exists : "+email);
    }
}
