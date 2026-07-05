package com.parking.parking_slot_booking_system.service.impl;

import com.parking.parking_slot_booking_system.dto.request.ParkingLotRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingLotResponse;
import com.parking.parking_slot_booking_system.dto.response.UserResponse;
import com.parking.parking_slot_booking_system.entity.ParkingLot;
import com.parking.parking_slot_booking_system.entity.User;
import com.parking.parking_slot_booking_system.enums.Role;
import com.parking.parking_slot_booking_system.exception.UnauthorizedException;
import com.parking.parking_slot_booking_system.mapper.ParkingLotMapper;
import com.parking.parking_slot_booking_system.mapper.UserMapper;
import com.parking.parking_slot_booking_system.repository.ParkingLotRepository;
import com.parking.parking_slot_booking_system.service.ParkingLotService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final UserMapper userMapper;
    private final ParkingLotMapper parkingLotMapper;
    public ParkingLotServiceImpl(ParkingLotRepository parkingLotRepository,
                                 UserMapper userMapper,
                                 ParkingLotMapper parkingLotMapper
    ){
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ParkingLotResponse createParkingLot(ParkingLotRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currUser = (User)authentication.getPrincipal();
//        System.out.println(user.getEmail());
//        System.out.println(user.getRole());
        if (currUser.getRole() != Role.PARTNER) {
            throw new UnauthorizedException("Only partners can create parking lots.");
        }
        ParkingLot parkingLot = parkingLotMapper.toParkingLot(request);

        // Business Logic
        parkingLot.setAvailableSlots(request.getTotalSlots()); //not done by mapper
       // Logged-in Partner
        parkingLot.setOwner(currUser);
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
        return parkingLotMapper.toParkingLotResponse(savedParkingLot);

    }


}
