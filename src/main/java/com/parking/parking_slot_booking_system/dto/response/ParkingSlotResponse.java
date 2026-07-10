package com.parking.parking_slot_booking_system.dto.response;

import com.parking.parking_slot_booking_system.enums.SlotStatus;
import com.parking.parking_slot_booking_system.enums.VehicleType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotResponse {
    private Long id;
    private String slotNumber;
    private VehicleType vehicleType;
    private SlotStatus slotStatus;
    private BigDecimal pricePerHour;
}
