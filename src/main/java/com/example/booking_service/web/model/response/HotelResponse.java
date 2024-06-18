package com.example.booking_service.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

    private Long id;

    private String name;

    private String title;

    private String city;

    private String address;

    private Double distance;

    private Double rating;

    private Integer numberOfRatings;

}
