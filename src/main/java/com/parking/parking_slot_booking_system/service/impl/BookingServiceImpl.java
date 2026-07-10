package com.parking.parking_slot_booking_system.service.impl;

import com.parking.parking_slot_booking_system.dto.request.BookingRequest;
import com.parking.parking_slot_booking_system.dto.response.BookingResponse;
import com.parking.parking_slot_booking_system.entity.Booking;
import com.parking.parking_slot_booking_system.entity.ParkingSlot;
import com.parking.parking_slot_booking_system.entity.User;
import com.parking.parking_slot_booking_system.enums.BookingStatus;
import com.parking.parking_slot_booking_system.enums.Role;
import com.parking.parking_slot_booking_system.enums.SlotStatus;
import com.parking.parking_slot_booking_system.exception.DuplicateResourceException;
import com.parking.parking_slot_booking_system.exception.InvalidRequestException;
import com.parking.parking_slot_booking_system.exception.ResourceNotFoundException;
import com.parking.parking_slot_booking_system.exception.UnauthorizedException;
import com.parking.parking_slot_booking_system.mapper.BookingMapper;
import com.parking.parking_slot_booking_system.repository.BookingRepository;
import com.parking.parking_slot_booking_system.repository.ParkingSlotRepository;
import com.parking.parking_slot_booking_system.service.BookingService;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final BookingMapper bookingMapper;
    public BookingServiceImpl(
            BookingRepository bookingRepository,
            ParkingSlotRepository parkingSlotRepository,
            BookingMapper bookingMapper
    ){
        this.bookingRepository = bookingRepository;
        this.parkingSlotRepository = parkingSlotRepository;
        this.bookingMapper = bookingMapper;
    }
    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request){
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currUser.getRole() == Role.PARTNER || currUser.getRole() == Role.ADMIN){
            throw new UnauthorizedException("Only Customers can create bookings.");
        }

        ParkingSlot parkingSlot = parkingSlotRepository
                .findById(request.getParkingSlotId())
                .orElseThrow(()->new ResourceNotFoundException("Parking slot not found with id:"+request.getParkingSlotId()));
        if(request.getVehicleType() != parkingSlot.getVehicleType())
            throw new InvalidRequestException("Selected parking slot does not support "+request.getVehicleType()+" vehicles.");

        if(request.getStartTime().isBefore(LocalDateTime.now()))
            throw new InvalidRequestException("Booking start time must be in the future.");

        if(!request.getEndTime().isAfter(request.getStartTime()))
            throw new InvalidRequestException("End time must be after start time.");
        if (parkingSlot.getSlotStatus() != SlotStatus.AVAILABLE) {
            throw new InvalidRequestException(
                    "Parking slot is currently unavailable."
            );
        }
        Duration duration = Duration.between(request.getStartTime(), request.getEndTime());
        if(duration.toMinutes() < 30)
            throw new InvalidRequestException("Minimum booking duration is 30 minutes.");

        if(bookingRepository.existsOverlappingBooking(parkingSlot,request.getStartTime(),request.getEndTime())){
            throw new DuplicateResourceException("The selected parking slot is already booked for the requested time");
        }


        BigDecimal hours = BigDecimal.valueOf(duration.toMinutes()).divide(BigDecimal.valueOf(60),2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = parkingSlot.getPricePerHour().multiply(hours);
        Booking booking = new Booking();
        booking.setCustomer(currUser);
        booking.setParkingSlot(parkingSlot);
        booking.setVehicleNumber(request.getVehicleNumber());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setBookingStatus(BookingStatus.BOOKED);
        booking.setTotalAmount(totalAmount);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toBookingResponse(savedBooking);
    }

    @Override
    public List<BookingResponse> getMyBookings(){
        User currUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currUser.getRole() != Role.CUSTOMER) {
            throw new UnauthorizedException("Only customers can view their bookings.");
        }
        List<Booking> bookings = bookingRepository.findByCustomerOrderByStartTimeDesc(currUser);
        List<BookingResponse> bookingResponse = new ArrayList<>();
        for(Booking booking:bookings){
            bookingResponse.add(bookingMapper.toBookingResponse(booking));
        }
        return bookingResponse;
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId) {
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Booking not found"));

        if(!booking.getCustomer().getId().equals(currUser.getId())){
            throw new UnauthorizedException("Your are not allowed to cancel this booking");
        }
        if(booking.getBookingStatus() == BookingStatus.CANCELLED)
            throw new InvalidRequestException("Booking is already cancelled");
        if(booking.getBookingStatus() == BookingStatus.COMPLETED)
            throw new InvalidRequestException("Completed booking cannot be cancelled");

        if(!booking.getStartTime().isAfter(LocalDateTime.now()))
            throw new InvalidRequestException("Booking has already started and cannot be cancelled");
        booking.setBookingStatus(BookingStatus.CANCELLED);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toBookingResponse(savedBooking);
    }

    @Override
    @Transactional
    public void completeExpiredBookings(){
        List<Booking> bookings = bookingRepository.findByBookingStatusAndEndTimeBefore(BookingStatus.BOOKED,LocalDateTime.now());
        System.out.println(bookings);
        for(Booking booking:bookings){
            booking.setBookingStatus(BookingStatus.COMPLETED);
        }
        bookingRepository.saveAll(bookings);
    }

    @Override
    public List<BookingResponse> getPartnerBookings(){
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Booking> bookings = bookingRepository.findByParkingSlotParkingLotOwnerOrderByStartTimeDesc(currUser);
        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .toList();
    }
}
