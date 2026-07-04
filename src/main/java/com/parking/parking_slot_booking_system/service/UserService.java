package com.parking.parking_slot_booking_system.service;

import com.parking.parking_slot_booking_system.dto.request.LoginRequest;
import com.parking.parking_slot_booking_system.dto.request.RegisterRequest;
import com.parking.parking_slot_booking_system.dto.response.LoginResponse;
import com.parking.parking_slot_booking_system.dto.response.UserResponse;

public interface UserService {
    UserResponse registerCustomer(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
