package com.parking.parking_slot_booking_system.dto.response;

import com.parking.parking_slot_booking_system.enums.BookingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private String parkingLotName;
    private String slotNumber;
    private String vehicleNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus bookingStatus;
    private BigDecimal totalAmount;
}
