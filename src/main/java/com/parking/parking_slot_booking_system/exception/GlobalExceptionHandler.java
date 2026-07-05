package com.parking.parking_slot_booking_system.exception;

import com.parking.parking_slot_booking_system.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler{
    private ErrorResponse buildErrorResponse(HttpStatus status, String message,HttpServletRequest request,Map<String,String> validationErrors){
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .validationErrors(validationErrors)
                .build();
    }
    private ErrorResponse buildErrorResponse(HttpStatus status, String message,HttpServletRequest request){
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex,
            HttpServletRequest request
    ){
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.CONFLICT.value())
//                .error(HttpStatus.CONFLICT.getReasonPhrase())
//                .message(ex.getMessage())
//                .path(request.getRequestURI())
//                .build();


        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(),request);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){

           String message = "Validation Failed";

            Map<String,String> validationErrors = new LinkedHashMap<>();
            for(FieldError error:ex.getBindingResult().getFieldErrors()){
                validationErrors.put(error.getField(),
                        Objects.requireNonNullElse(
                                error.getDefaultMessage(),
                                "Validation failed"
                        ));
            }
//           ErrorResponse errorResponse = ErrorResponse.builder()
//                   .timestamp(LocalDateTime.now())
//                   .status(HttpStatus.BAD_REQUEST.value())
//                   .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
//                   .message(message)
//                   .path(request.getRequestURI())
//                   .validationErrors(validationErrors)
//                   .build();

        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST,message,request,validationErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException ex,
            HttpServletRequest request
    ){
        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(),request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex,
            HttpServletRequest request
    ){
        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.FORBIDDEN,ex.getMessage(),request);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
