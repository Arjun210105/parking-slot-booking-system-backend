package com.parking.parking_slot_booking_system.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "First name is Required")
    private String firstName;
    @NotBlank(message = "Last name is Required")
    private String lastName;
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Phone Number is Required")
    @Pattern(
            regexp =  "^[6-9]\\d{9}$",
            message = "Phone Number must be a Valid 10 digit Indian Number"
    )
    private String phoneNumber;
    @NotBlank(message = "Password is required")
    @Size(min = 8,max = 20,
            message = "Password must be between 8 and 20 characters")
    private String password;
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;
}
