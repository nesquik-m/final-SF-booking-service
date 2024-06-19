package com.example.booking_service.web.model.response;

import com.example.booking_service.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private Long id;

    private String name;

    private String description;

    private Integer roomNumber;

    private BigDecimal price;

    private Integer maxNumberOfPeople;

    private String hotelName;

}
