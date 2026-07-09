package com.parking.parking_slot_booking_system.mapper;

import com.parking.parking_slot_booking_system.dto.request.ParkingSlotRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingSlotResponse;
import com.parking.parking_slot_booking_system.entity.ParkingSlot;
import org.springframework.stereotype.Component;

@Component
public class ParkingSlotMapper {
    public ParkingSlot toParkingSlot(ParkingSlotRequest request) {
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotNumber(request.getSlotNumber());
        parkingSlot.setVehicleType(request.getVehicleType());
        return parkingSlot;
    }

    public ParkingSlotResponse toParkingSlotResponse(ParkingSlot savedParkingSlot) {
        ParkingSlotResponse response = new ParkingSlotResponse();
        response.setId(savedParkingSlot.getId());
        response.setSlotNumber(savedParkingSlot.getSlotNumber());
        response.setVehicleType(savedParkingSlot.getVehicleType());
        response.setSlotStatus(savedParkingSlot.getSlotStatus());
        return response;
    }
}
