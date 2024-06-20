package com.example.booking_service.service;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.web.model.request.PageableRequest;

import java.util.List;

public interface BookingService {

    List<Booking> findAllBookings(PageableRequest request);

    Booking bookARoom(Booking booking);

}
