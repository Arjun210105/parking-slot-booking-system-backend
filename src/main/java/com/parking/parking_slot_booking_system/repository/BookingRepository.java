package com.parking.parking_slot_booking_system.repository;

import com.parking.parking_slot_booking_system.entity.Booking;
import com.parking.parking_slot_booking_system.entity.ParkingSlot;
import com.parking.parking_slot_booking_system.entity.User;
import com.parking.parking_slot_booking_system.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("""
        SELECT COUNT(b) > 0
        FROM Booking b
        WHERE b.parkingSlot = :parkingSlot
        AND b.bookingStatus = com.parking.parking_slot_booking_system.enums.BookingStatus.BOOKED
        AND :startTime < b.endTime
        AND :endTime > b.startTime"""
    )
    boolean existsOverlappingBooking(
            @Param("parkingSlot")ParkingSlot parkingSlot,
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    //List<Booking> findByCustomer(User customer);
    List<Booking> findByCustomerOrderByStartTimeDesc(User customer);
    List<Booking> findByBookingStatusAndEndTimeBefore(
            BookingStatus bookingStatus,
            LocalDateTime endTime
    );

    List<Booking> findByParkingSlotParkingLotOwnerOrderByStartTimeDesc(User owner);
}
