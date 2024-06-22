package com.example.booking_service.web.controller;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.web.model.request.BookingRequest;
import com.example.booking_service.web.model.request.PageableRequest;
import com.example.booking_service.web.model.response.BookingResponse;
import com.example.booking_service.web.model.response.BookingResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingResponseList> findAllBookings(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                bookingMapper.bookingListToBookingResponseList(
                        bookingService.findAllBookings(request)));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookARoom(@RequestBody @Valid BookingRequest request) {
        Booking bookedRoom = bookingService.bookARoom(bookingMapper.requestToBooking(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingMapper.bookingToBookingResponse(bookedRoom));
    }

}
