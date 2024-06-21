package com.example.booking_service.web.controller;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.mapper.HotelMapper;
import com.example.booking_service.service.HotelService;
import com.example.booking_service.web.model.request.HotelFilter;
import com.example.booking_service.web.model.request.HotelRequest;
import com.example.booking_service.web.model.request.PageableRequest;
import com.example.booking_service.web.model.response.HotelResponseList;
import com.example.booking_service.web.model.response.HotelResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @GetMapping("/filter")
    public ResponseEntity<HotelResponseList> filterBy(HotelFilter filter, PageableRequest pageable) {
        Page<Hotel> result = hotelService.filterBy(filter, pageable);
        return ResponseEntity.ok().body(hotelMapper.hotelListToHotelResponseList(result.getTotalElements(), result.getContent()));
    }

    @GetMapping
    public ResponseEntity<HotelResponseList> findAllHotels(@Valid PageableRequest request) {
        Page<Hotel> result = hotelService.findAllHotels(request);
        return ResponseEntity.ok(
                hotelMapper.hotelListToHotelResponseList(result.getTotalElements(), result.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findHotelById(@PathVariable("id") Long hotelId) {
        return ResponseEntity.ok().body(hotelMapper.hotelToHotelResponse(hotelService.findHotelById(hotelId)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody @Valid HotelRequest request) {
        Hotel createdHotel = hotelService.createHotel(hotelMapper.requestToHotel(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelMapper.hotelToHotelResponse(createdHotel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable("id") Long hotelId,
                                                     @RequestBody @Valid HotelRequest request) {
        Hotel updatedHotel = hotelService.updateHotel(hotelId, hotelMapper.requestToHotel(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelMapper.hotelToHotelResponse(updatedHotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteHotelById(@PathVariable("id") Long hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/rating/{id}")
    public ResponseEntity<Void> giveARating(@PathVariable("id") Long hotelId,
                                            @RequestParam @Range(min = 1, max = 5) int newMark) {
        hotelService.giveARating(hotelId, newMark);
        return ResponseEntity.ok().build();
    }

}
