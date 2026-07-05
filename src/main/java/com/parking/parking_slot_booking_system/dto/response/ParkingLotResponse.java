package com.parking.parking_slot_booking_system.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLotResponse {
    private Long id;

    private String name;

    private String description;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private Double latitude;

    private Double longitude;

    private Integer totalSlots;

    private Integer availableSlots;

    private UserResponse owner;
}
