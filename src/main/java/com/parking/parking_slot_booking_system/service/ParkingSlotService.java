package com.parking.parking_slot_booking_system.service;

import com.parking.parking_slot_booking_system.dto.request.ParkingSlotRequest;
import com.parking.parking_slot_booking_system.dto.request.ParkingSlotUpdateRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingSlotResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ParkingSlotService {
    ParkingSlotResponse createParkingSlot(Long parkingLotId, ParkingSlotRequest request);
    List<ParkingSlotResponse> getParkingSlots(Long parkingLotId);
    ParkingSlotResponse updateParkingSlot(Long slotId, ParkingSlotUpdateRequest request);
    void deleteParkingSlot(Long slotId);
}
