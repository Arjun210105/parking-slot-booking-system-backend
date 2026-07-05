package com.parking.parking_slot_booking_system.repository;

import com.parking.parking_slot_booking_system.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long>{
    
}
