package com.parking.parking_slot_booking_system.dto.request;

import com.parking.parking_slot_booking_system.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSlotUpdateRequest{
    @NotBlank
    @Size(max = 20)
    private String slotNumber;
    @NotNull
    private VehicleType vehicleType;
}
