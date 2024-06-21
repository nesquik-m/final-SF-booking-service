package com.example.booking_service.web.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RoomFilter {

    private Long roomId;

    private String description;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer maxNumberOfPeople;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long hotelId;

}
