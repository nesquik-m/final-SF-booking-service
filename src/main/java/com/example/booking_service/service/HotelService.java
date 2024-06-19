package com.example.booking_service.service;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.request.PageableRequest;

import java.util.List;

public interface HotelService {

    List<Hotel> findAllHotels(PageableRequest request);

    Hotel findHotelById(Long hotelId);

    Hotel createHotel(Hotel hotel);

    Hotel updateHotel(Long hotelId, Hotel hotel);

    void deleteHotelById(Long hotelId);
}
