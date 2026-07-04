package com.parking.parking_slot_booking_system.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Invalid email or Password");
    }
}
