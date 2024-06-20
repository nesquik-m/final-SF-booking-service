package com.example.booking_service.web.controller;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.mapper.HotelMapper;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.request.HotelRequest;
import com.example.booking_service.web.model.request.PageableRequest;
import com.example.booking_service.web.model.response.HotelResponse;
import com.example.booking_service.web.model.response.HotelResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<HotelResponseList> findAllHotels(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                hotelMapper.hotelListToHotelResponseList(
                        hotelService.findAllHotels(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findHotelById(@PathVariable("id") Long hotelId) {
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
    public ResponseEntity<Void> deleteHotelById(@PathVariable("id") Long hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
