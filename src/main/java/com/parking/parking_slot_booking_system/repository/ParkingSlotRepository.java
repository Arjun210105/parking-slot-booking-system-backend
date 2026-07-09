package com.parking.parking_slot_booking_system.repository;

import com.parking.parking_slot_booking_system.entity.ParkingLot;
import com.parking.parking_slot_booking_system.entity.ParkingSlot;
import com.parking.parking_slot_booking_system.enums.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot,Long> {
    boolean existsBySlotNumberAndParkingLot(
            String slotNumber,
            ParkingLot parkingLot
    );
    List<ParkingSlot> findByParkingLot(ParkingLot parkingLot);
    List<ParkingSlot> findByParkingLotAndSlotStatus(ParkingLot parkingLot, SlotStatus slotStatus);
}
