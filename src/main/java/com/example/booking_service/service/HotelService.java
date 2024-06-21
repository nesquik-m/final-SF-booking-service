package com.example.booking_service.service;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.request.HotelFilter;
import com.example.booking_service.web.model.request.PageableRequest;
import org.springframework.data.domain.Page;

public interface HotelService {

    Page<Hotel> filterBy(HotelFilter filter, PageableRequest pageable);

    Page<Hotel> findAllHotels(PageableRequest request);

    Hotel findHotelById(Long hotelId);

    Hotel createHotel(Hotel hotel);

    Hotel updateHotel(Long hotelId, Hotel hotel);

    void deleteHotelById(Long hotelId);

    void giveARating(Long hotelId, int newMark);
}
