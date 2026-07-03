package com.parking.parking_slot_booking_system.controller;

import com.parking.parking_slot_booking_system.dto.request.RegisterRequest;
import com.parking.parking_slot_booking_system.dto.response.UserResponse;
import com.parking.parking_slot_booking_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService service){
        this.userService = service;
    }

    @PostMapping("/register/customer")
    public ResponseEntity<UserResponse> registerCustomer(@RequestBody RegisterRequest request){
        UserResponse response = userService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
