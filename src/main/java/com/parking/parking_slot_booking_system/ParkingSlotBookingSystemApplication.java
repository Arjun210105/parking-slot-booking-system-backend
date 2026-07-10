package com.parking.parking_slot_booking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ParkingSlotBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingSlotBookingSystemApplication.class, args);
	}

}
