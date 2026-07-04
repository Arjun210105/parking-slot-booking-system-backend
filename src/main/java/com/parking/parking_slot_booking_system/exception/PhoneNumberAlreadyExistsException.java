package com.parking.parking_slot_booking_system.exception;

public class PhoneNumberAlreadyExistsException extends RuntimeException{
    public PhoneNumberAlreadyExistsException(String phoneNumber){
        super("Phone Number already exits :"+phoneNumber);
    }
}
