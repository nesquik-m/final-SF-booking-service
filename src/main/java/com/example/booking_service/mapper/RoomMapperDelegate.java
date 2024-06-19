package com.example.booking_service.mapper;

import com.example.booking_service.entity.Room;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.request.RoomRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RoomMapperDelegate implements RoomMapper {

    @Autowired
    private HotelService hotelService;

    @Override
    public Room requestToRoom(RoomRequest request) {
        return Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .roomNumber(request.getRoomNumber())
                .price(request.getPrice())
                .maxNumberOfPeople(request.getMaxNumberOfPeople())
                .hotel(hotelService.findHotelById(request.getHotelId()))
                .build();
    }

}
