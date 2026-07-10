package com.parking.parking_slot_booking_system.service.impl;

import com.parking.parking_slot_booking_system.dto.request.ParkingSlotRequest;
import com.parking.parking_slot_booking_system.dto.request.ParkingSlotUpdateRequest;
import com.parking.parking_slot_booking_system.dto.response.ParkingSlotResponse;
import com.parking.parking_slot_booking_system.entity.ParkingLot;
import com.parking.parking_slot_booking_system.entity.ParkingSlot;
import com.parking.parking_slot_booking_system.entity.User;
import com.parking.parking_slot_booking_system.enums.Role;
import com.parking.parking_slot_booking_system.enums.SlotStatus;
import com.parking.parking_slot_booking_system.exception.DuplicateResourceException;
import com.parking.parking_slot_booking_system.exception.ResourceNotFoundException;
import com.parking.parking_slot_booking_system.exception.UnauthorizedException;
import com.parking.parking_slot_booking_system.mapper.ParkingSlotMapper;
import com.parking.parking_slot_booking_system.repository.ParkingLotRepository;
import com.parking.parking_slot_booking_system.repository.ParkingSlotRepository;
import com.parking.parking_slot_booking_system.service.ParkingSlotService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {
    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSlotMapper parkingSlotMapper;

    ParkingSlotServiceImpl(
            ParkingSlotRepository parkingSlotRepository,
            ParkingLotRepository parkingLotRepository,
            ParkingSlotMapper parkingSlotMapper
    ){
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingSlotMapper = parkingSlotMapper;
    }
    @Override
    @Transactional
    public ParkingSlotResponse createParkingSlot(Long parkingLotId, ParkingSlotRequest request){
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currUser.getRole() != Role.PARTNER){
            throw new UnauthorizedException( "Only partners can create parking slots.");
        }

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(()->
                        new ResourceNotFoundException(
                                "Parking lot not found with id:" + parkingLotId
                        )
                );

        //OwnerShip Validation
        if(!parkingLot.getOwner().getId().equals(currUser.getId())){
            throw new UnauthorizedException(
                    "You can only manage your own parking lots."
            );
        }

        if(parkingSlotRepository.existsBySlotNumberAndParkingLot(
                request.getSlotNumber(),
                parkingLot
        )){
            throw new DuplicateResourceException("Slot number '" + request.getSlotNumber()
                    + "' already exists in this parking lot.");
        }

        ParkingSlot parkingSlot = parkingSlotMapper.toParkingSlot(request);
        parkingSlot.setParkingLot(parkingLot);
        parkingSlot.setSlotStatus(SlotStatus.AVAILABLE);
        parkingSlot.setPricePerHour(request.getPricePerHour());
        ParkingSlot savedParkingSlot = parkingSlotRepository.save(parkingSlot);
        return parkingSlotMapper.toParkingSlotResponse(savedParkingSlot);
    }
    
    public List<ParkingSlotResponse> getParkingSlots(Long parkingLotId){
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ParkingLot parkingLot = parkingLotRepository
                .findById(parkingLotId)
                .orElseThrow(()->new ResourceNotFoundException("Parking lot not found with id:"+parkingLotId));
        List<ParkingSlot> parkingSlots = new ArrayList<>();
        if(currUser.getRole() == Role.CUSTOMER){
            parkingSlots = parkingSlotRepository.findByParkingLotAndSlotStatus(parkingLot,SlotStatus.AVAILABLE);
        }else{
            if(currUser.getRole() == Role.PARTNER && !parkingLot.getOwner().getId().equals(currUser.getId())){
                throw new UnauthorizedException("You can only view your own parking slots");
            }
            //here the user will be valid partner or admin so both will get all slots
            parkingSlots = parkingSlotRepository.findByParkingLot(parkingLot);
        }
        List<ParkingSlotResponse> responses = new ArrayList<>();
        for(ParkingSlot parkingSlot:parkingSlots){
            responses.add(parkingSlotMapper.toParkingSlotResponse(parkingSlot));
        }
        return responses;
    }

    @Override
    @Transactional
    public ParkingSlotResponse updateParkingSlot(
            Long slotId,
            ParkingSlotUpdateRequest request
    ){
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ParkingSlot parkingSlot = parkingSlotRepository
                .findById(slotId)
                .orElseThrow(()->new ResourceNotFoundException("Parking slot not found with id:"+slotId));
        ParkingLot parkingLot = parkingSlot.getParkingLot();
        if(currUser.getRole() == Role.CUSTOMER) throw new UnauthorizedException("You are not allowed to update slots.");
        if(currUser.getRole() == Role.PARTNER && !parkingLot.getOwner().getId().equals(currUser.getId()))
            throw new UnauthorizedException("You can only update your slots");
        if(!parkingSlot.getSlotNumber().equals(request.getSlotNumber())){
            if(parkingSlotRepository.existsBySlotNumberAndParkingLot(request.getSlotNumber(),parkingLot))
                throw new DuplicateResourceException("Slot number already exists");
        }
        parkingSlot.setSlotNumber(request.getSlotNumber());
        parkingSlot.setVehicleType(request.getVehicleType());
        parkingSlot.setPricePerHour(request.getPricePerHour());
        ParkingSlot updatedSlot = parkingSlotRepository.save(parkingSlot);
        return parkingSlotMapper.toParkingSlotResponse(updatedSlot);
    }

    @Override
    @Transactional
    public void deleteParkingSlot(Long slotId){
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currUser.getRole() == Role.CUSTOMER) throw new UnauthorizedException("You are not allowed to delete slot");
        ParkingSlot parkingSlot = parkingSlotRepository
                .findById(slotId)
                .orElseThrow(()->new ResourceNotFoundException("Parking slot not found with id:"+slotId));
        ParkingLot parkingLot = parkingSlot.getParkingLot();
        if(currUser.getRole() == Role.PARTNER && !parkingLot.getOwner().getId().equals(currUser.getId())){
            throw new UnauthorizedException("You can only delete your own parking slots.");
        }
        // TODO (Booking Module):
        // Before deleting a parking slot, verify that there are no active or future bookings
        // associated with this slot. If bookings exist, reject the delete operation.
        // Reason: Deleting a slot with active bookings would lead to data inconsistency.
        parkingSlotRepository.delete(parkingSlot);
    }
}
