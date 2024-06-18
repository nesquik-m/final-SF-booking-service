package com.example.booking_service.web.controller;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.mapper.HotelMapper;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.request.HotelRequest;
import com.example.booking_service.web.model.response.HotelResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    // TODO: Настроить валидацию данных на HotelRequest

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() { // TODO: написать потом как все остальные
        return ResponseEntity.ok(
                hotelService.findAllHotels().stream()
                .map(hotelMapper::hotelToHotelResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable("id") Long hotelId) {
        return ResponseEntity.ok().body(hotelMapper.hotelToHotelResponse(hotelService.findHotelById(hotelId)));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@RequestBody @Valid HotelRequest request) {
        Hotel createdHotel = hotelService.createHotel(hotelMapper.requestToHotel(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelMapper.hotelToHotelResponse(createdHotel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable("id") Long hotelId,
                                                     @RequestBody @Valid HotelRequest request) {
        Hotel updatedHotel = hotelService.updateHotel(hotelId, hotelMapper.requestToHotel(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelMapper.hotelToHotelResponse(updatedHotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
