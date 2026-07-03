package com.parking.parking_slot_booking_system.entity;

import com.parking.parking_slot_booking_system.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity //This tells hibernate that it is a table
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id //it will mark id as primary key for this table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment id
    private Long Id;
    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(nullable = false,length=50)
    private String lastName;
    @Column(nullable = false,unique = true, length=100)
    private String email;
    @Column(nullable = false,unique = true, length=15)
    private String phoneNumber;
    @Column(nullable = false,length = 255)
    private String password;
    private LocalDate dateOfBirth;
    @Column(length = 255)
    private String profileImageUrl;

    //Advanced fields
    @Enumerated(EnumType.STRING) //tells db the enum type string
    @Column(nullable = false)
    private Role role; // stores enum type
    @Column(nullable = false)
    private Boolean isActive = true; // tells does account is active
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp //hibernte automatically updates timestamp
    private LocalDateTime updatedAt;
}
