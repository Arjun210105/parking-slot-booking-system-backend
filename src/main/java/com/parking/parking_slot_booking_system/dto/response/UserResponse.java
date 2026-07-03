package com.parking.parking_slot_booking_system.dto.response;

import com.parking.parking_slot_booking_system.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String profileImageUrl;
    Role role;
}
