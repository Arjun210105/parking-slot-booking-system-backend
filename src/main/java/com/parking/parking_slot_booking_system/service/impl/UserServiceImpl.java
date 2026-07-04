package com.parking.parking_slot_booking_system.service.impl;

import com.parking.parking_slot_booking_system.dto.request.LoginRequest;
import com.parking.parking_slot_booking_system.dto.request.RegisterRequest;
import com.parking.parking_slot_booking_system.dto.response.LoginResponse;
import com.parking.parking_slot_booking_system.dto.response.UserResponse;
import com.parking.parking_slot_booking_system.entity.User;
import com.parking.parking_slot_booking_system.enums.Role;
import com.parking.parking_slot_booking_system.exception.EmailAlreadyExistsException;
import com.parking.parking_slot_booking_system.exception.InvalidCredentialsException;
import com.parking.parking_slot_booking_system.exception.PhoneNumberAlreadyExistsException;
import com.parking.parking_slot_booking_system.repository.UserRepository;
import com.parking.parking_slot_booking_system.security.JwtService;
import com.parking.parking_slot_booking_system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private UserResponse  mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setProfileImageUrl(user.getProfileImageUrl());
        response.setRole(user.getRole());
        return response;
    }
    public UserResponse registerCustomer(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new PhoneNumberAlreadyExistsException(request.getPhoneNumber());
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
        return mapToUserResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request){
        //finding if user exists in the db if there return object else throw exception
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        //comparing password with the db password if not matched Invalid cred
        if(!passwordEncoder.matches(request.getPassword(),
                user.getPassword())){
            throw new InvalidCredentialsException();
        }

        UserResponse userResponse = mapToUserResponse(user);

        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .user(userResponse)
                .build();
    }
}
