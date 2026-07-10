package com.parking.parking_slot_booking_system.entity;

import com.parking.parking_slot_booking_system.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",nullable = false)
    private User customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_slot_id",nullable = false)
    private ParkingSlot parkingSlot;
    @Column(nullable = false)
    private String vehicleNumber;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;
    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal totalAmount;
}
