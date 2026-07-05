package com.parking.parking_slot_booking_system.service;

import com.parking.parking_slot_booking_system.dto.request.ParkingLotRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingLotResponse;

public interface ParkingLotService {
    ParkingLotResponse createParkingLot(ParkingLotRequest request);
}
