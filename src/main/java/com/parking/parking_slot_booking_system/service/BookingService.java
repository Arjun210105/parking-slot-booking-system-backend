package com.parking.parking_slot_booking_system.service;

import com.parking.parking_slot_booking_system.dto.request.BookingRequest;
import com.parking.parking_slot_booking_system.dto.response.BookingResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> getMyBookings();
    BookingResponse cancelBooking(Long bookingId);
    void completeExpiredBookings();
    List<BookingResponse> getPartnerBookings();
}
