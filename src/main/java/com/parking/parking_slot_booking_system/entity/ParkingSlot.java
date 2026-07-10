package com.parking.parking_slot_booking_system.entity;

import com.parking.parking_slot_booking_system.enums.SlotStatus;
import com.parking.parking_slot_booking_system.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSlot extends BaseEntity{

    private String slotNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private BigDecimal pricePerHour;

    @Enumerated(EnumType.STRING)
    private SlotStatus slotStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id",nullable = false)
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "parkingSlot")
    private List<Booking> bookings = new ArrayList<>();
}
