package com.parking.parking_slot_booking_system.repository;

import com.parking.parking_slot_booking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Spring Data JPA generates the implementation automatically at runtime using proxy classes.
//this will create all the methods at runtime
//like save(User) ,findAll() jpa creates them
//save(User user){//implementation code }
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
