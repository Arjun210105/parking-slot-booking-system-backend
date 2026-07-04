package com.parking.parking_slot_booking_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse{
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String,String> validationErrors;
}
