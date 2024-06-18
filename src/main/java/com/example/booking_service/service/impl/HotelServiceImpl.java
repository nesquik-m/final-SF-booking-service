package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.repository.HotelRepository;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAllHotels() { // пагинация
        return hotelRepository.findAll();
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
    public Hotel updateHotel(Long hotelId, Hotel hotel) {
        Hotel updatedHotel = findHotelById(hotelId);
        BeanUtils.copyNonNullProperties(hotel, updatedHotel);
        return hotelRepository.save(updatedHotel);
    }


    @Override
    public void deleteHotelById(Long hotelId) {
        findHotelById(hotelId); // убедимся, что такой id существует (иначе ловим исключение)
        hotelRepository.deleteById(hotelId);
    }

}
