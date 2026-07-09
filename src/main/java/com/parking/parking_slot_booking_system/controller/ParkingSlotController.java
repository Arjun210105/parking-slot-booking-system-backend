package com.parking.parking_slot_booking_system.controller;

import com.parking.parking_slot_booking_system.dto.request.ParkingSlotRequest;
import com.parking.parking_slot_booking_system.dto.request.ParkingSlotUpdateRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingSlotResponse;
import com.parking.parking_slot_booking_system.service.ParkingSlotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-lots/{parkingLotId}/slots")
public class ParkingSlotController {
    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService){
        this.parkingSlotService = parkingSlotService;
    }

    @PostMapping
    public ResponseEntity<ParkingSlotResponse> createParkingSlot(
            @PathVariable Long parkingLotId,
            @Valid @RequestBody ParkingSlotRequest request
    ) {

        ParkingSlotResponse response =
                parkingSlotService.createParkingSlot(parkingLotId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSlotResponse>> getParkingSlots(
            @PathVariable Long parkingLotId
    ){
        List<ParkingSlotResponse> response = parkingSlotService.getParkingSlots(parkingLotId);
        return ResponseEntity.ok(response);
    }


}
