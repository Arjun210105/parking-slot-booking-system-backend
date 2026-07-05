package com.parking.parking_slot_booking_system.mapper;

import com.parking.parking_slot_booking_system.dto.request.ParkingLotRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingLotResponse;
import com.parking.parking_slot_booking_system.entity.ParkingLot;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {
    private UserMapper userMapper;
    ParkingLotMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    public ParkingLotResponse toParkingLotResponse(ParkingLot parkingLot){
        ParkingLotResponse response = new ParkingLotResponse();
        response.setId(parkingLot.getId());
        response.setName(parkingLot.getName());
        response.setDescription(parkingLot.getDescription());
        response.setAddress(parkingLot.getAddress());
        response.setCity(parkingLot.getCity());
        response.setState(parkingLot.getState());
        response.setPincode(parkingLot.getPincode());
        response.setLatitude(parkingLot.getLatitude());
        response.setLongitude(parkingLot.getLongitude());
        response.setTotalSlots(parkingLot.getTotalSlots());
        response.setAvailableSlots(parkingLot.getAvailableSlots());
        response.setOwner(userMapper.toUserResponse(parkingLot.getOwner()));
        return response;
    }

    public ParkingLot toParkingLot(ParkingLotRequest request){
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(request.getName());
        parkingLot.setDescription(request.getDescription());
        parkingLot.setAddress(request.getAddress());
        parkingLot.setCity(request.getCity());
        parkingLot.setState(request.getState());
        parkingLot.setPincode(request.getPincode());
        parkingLot.setLatitude(request.getLatitude());
        parkingLot.setLongitude(request.getLongitude());
        parkingLot.setTotalSlots(request.getTotalSlots());
        return parkingLot;
    }
}
