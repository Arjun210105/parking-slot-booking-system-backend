package com.parking.parking_slot_booking_system.controller;

import com.parking.parking_slot_booking_system.dto.request.ParkingSlotRequest;
import com.parking.parking_slot_booking_system.dto.request.ParkingSlotUpdateRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingSlotResponse;
import com.parking.parking_slot_booking_system.service.ParkingSlotService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-slots")
public class ParkingSlotManagementController {
    private final ParkingSlotService parkingSlotService;
    public ParkingSlotManagementController(ParkingSlotService service){
        this.parkingSlotService = service;
    }
    @PutMapping("/{slotId}")
    public ResponseEntity<ParkingSlotResponse> updateParkingSlot(
            @PathVariable Long slotId,
            @Valid @RequestBody ParkingSlotUpdateRequest request
    ){
        ParkingSlotResponse response = parkingSlotService.updateParkingSlot(slotId,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteParkingSlot(
            @PathVariable Long slotId,
            @Valid @RequestBody ParkingSlotUpdateRequest request
    ){
        parkingSlotService.deleteParkingSlot(slotId);
        return ResponseEntity.noContent().build();
    }
}
