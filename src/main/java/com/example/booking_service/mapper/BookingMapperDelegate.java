package com.example.booking_service.mapper;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.service.UserService;
import com.example.booking_service.web.model.request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BookingMapperDelegate implements BookingMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    public Booking requestToBooking(BookingRequest request) {
        return Booking.builder()
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .user(userService.findUserById(request.getUserId()))
                .room(roomService.findRoomById(request.getRoomId()))
                .build();
    }

}
