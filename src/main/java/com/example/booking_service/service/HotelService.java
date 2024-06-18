package com.example.booking_service.service;

import com.example.booking_service.entity.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAllHotels();

    Hotel findHotelById(Long hotelId);

    Hotel createHotel(Hotel hotel);

    Hotel updateHotel(Long hotelId, Hotel hotel);

    void deleteHotelById(Long hotelId);
}
