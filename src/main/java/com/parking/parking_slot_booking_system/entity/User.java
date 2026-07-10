package com.parking.parking_slot_booking_system.entity;

import com.parking.parking_slot_booking_system.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity //This tells hibernate that it is a table
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

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
    private boolean isActive = true; // tells does account is active

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<ParkingLot> parkingLots = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public String getUsername() {
        return Objects.requireNonNull(email);
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public void setIsActive(boolean b) {
        this.isActive = b;
    }
}
