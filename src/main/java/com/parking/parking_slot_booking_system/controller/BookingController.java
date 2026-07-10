package com.parking.parking_slot_booking_system.controller;

import com.parking.parking_slot_booking_system.dto.request.BookingRequest;
import com.parking.parking_slot_booking_system.dto.response.BookingResponse;
import com.parking.parking_slot_booking_system.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request
    ){
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(){
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId)
    {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }


}
