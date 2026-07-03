package com.parking.parking_slot_booking_system.service.impl;

import com.parking.parking_slot_booking_system.dto.request.RegisterRequest;
import com.parking.parking_slot_booking_system.dto.response.UserResponse;
import com.parking.parking_slot_booking_system.entity.User;
import com.parking.parking_slot_booking_system.enums.Role;
import com.parking.parking_slot_booking_system.repository.UserRepository;
import com.parking.parking_slot_booking_system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerCustomer(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already Exits");
        }

        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new RuntimeException("Phone number already Exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setProfileImageUrl(null);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setIsActive(true);
        User savedUser = userRepository.save(user); //saving user into db

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setPhoneNumber(savedUser.getPhoneNumber());
        response.setProfileImageUrl(savedUser.getProfileImageUrl());
        response.setRole(savedUser.getRole());
        return response;
    }
}
