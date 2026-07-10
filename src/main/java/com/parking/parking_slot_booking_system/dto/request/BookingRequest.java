package com.parking.parking_slot_booking_system.dto.request;

import com.parking.parking_slot_booking_system.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    @NotNull
    private Long parkingSlotId;
    @NotNull
    private VehicleType vehicleType;
    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}$",
            message = "Invalid vehicle number format"
    )
    private String vehicleNumber;

    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}
