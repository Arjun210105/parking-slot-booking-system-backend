package com.parking.parking_slot_booking_system.mapper;

import com.parking.parking_slot_booking_system.dto.response.BookingResponse;
import com.parking.parking_slot_booking_system.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper{
    public BookingResponse toBookingResponse(Booking booking){
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setParkingLotName(booking.getParkingSlot().getParkingLot().getName());
        response.setSlotNumber(booking.getParkingSlot().getSlotNumber());
        response.setVehicleNumber(booking.getVehicleNumber());
        response.setStartTime(booking.getStartTime());
        response.setEndTime(booking.getEndTime());
        response.setBookingStatus(booking.getBookingStatus());
        response.setTotalAmount(booking.getTotalAmount());
        return response;
    }
}
