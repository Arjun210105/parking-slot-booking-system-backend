package com.parking.parking_slot_booking_system.dto.request;

import com.parking.parking_slot_booking_system.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotRequest{
    @NotBlank
    @Size(max=20)
    private String slotNumber;

    @NotNull
    private VehicleType vehicleType;
}
