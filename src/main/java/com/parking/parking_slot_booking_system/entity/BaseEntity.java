package com.parking.parking_slot_booking_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id //it will mark id as primary key for this table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment id
    private Long Id;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp //hibernate automatically updates timestamp
    private LocalDateTime updatedAt;
}
