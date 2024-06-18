package com.example.booking_service.mapper;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.request.HotelRequest;

import java.util.UUID;

public abstract class HotelMapperDelegate implements HotelMapper {

    @Override
    public Hotel requestToHotel(HotelRequest request) {
        return Hotel.builder()
                .name(request.getName())
                .title(request.getTitle())
                .city(request.getCity())
                .address(request.getAddress())
                .distance(request.getDistance())
                .rating(0.0)
                .numberOfRatings(0)
                .build();
    }
}
