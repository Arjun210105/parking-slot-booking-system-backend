package com.parking.parking_slot_booking_system.scheduler;

import com.parking.parking_slot_booking_system.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingScheduler {
    private final BookingService bookingService;
    @Scheduled(cron = "0 * * * * *")
    public void completeExpiredBookings(){
        bookingService.completeExpiredBookings();
    }
}
