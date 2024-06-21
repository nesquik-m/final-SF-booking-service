package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.repository.HotelRepository;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.utils.BeanUtils;
import com.example.booking_service.web.model.request.PageableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAllHotels(PageableRequest request) {
        return hotelRepository.findAll(request.pageRequest()).getContent();
    }

    @Override
    public Hotel findHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Отель с ID {0} не найден!", hotelId)));
    }

    @Override
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    @Transactional
    public Hotel updateHotel(Long hotelId, Hotel hotel) {
        Hotel updatedHotel = findHotelById(hotelId);
        BeanUtils.copyNonNullProperties(hotel, updatedHotel);
        return hotelRepository.save(updatedHotel);
    }


    @Override
    @Transactional
    public void deleteHotelById(Long hotelId) {
        findHotelById(hotelId);
        hotelRepository.deleteById(hotelId);
    }

    @Override
    @Transactional
    public void giveARating(Long hotelId, int newMark) {
        Hotel hotel = findHotelById(hotelId);

        double rating = hotel.getRating();
        int numberOfRatings = hotel.getNumberOfRatings();
        double totalRating = rating * numberOfRatings;

        totalRating = totalRating - rating + newMark;
        rating = Math.round((numberOfRatings == 0 ? totalRating : totalRating / numberOfRatings) * 10) / 10.0;
        numberOfRatings++;

        hotel.setRating(rating);
        hotel.setNumberOfRatings(numberOfRatings);
        hotelRepository.save(hotel);
    }

}
