package com.parking.parking_slot_booking_system.controller;


import com.parking.parking_slot_booking_system.dto.request.ParkingLotRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingLotResponse;
import com.parking.parking_slot_booking_system.service.ParkingLotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parking-lots")
public class ParkingLotController{
    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService){
        this.parkingLotService = parkingLotService;
    }

    @PostMapping
    public ResponseEntity<ParkingLotResponse> createParkingLot(
            @Valid @RequestBody ParkingLotRequest request
    ){
        ParkingLotResponse response = parkingLotService.createParkingLot(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
