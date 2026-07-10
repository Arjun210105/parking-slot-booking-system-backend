package com.parking.parking_slot_booking_system.controller;

import com.parking.parking_slot_booking_system.dto.response.BookingResponse;
import com.parking.parking_slot_booking_system.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final BookingService bookingService;
    @PreAuthorize("hasRole('PARTNER')")
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getPartnerBookings(){
        return ResponseEntity.ok(bookingService.getPartnerBookings());
    }
}
