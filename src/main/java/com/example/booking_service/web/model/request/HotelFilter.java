package com.example.booking_service.web.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelFilter {

    private Integer pageSize;

    private Integer pageNumber;

    private Long hotelId;

    private String nameHotel;

    private String title;

    private String city;

    private String address;

    private Double distance;

    private Double rating;

    private Integer numberOfRatings;

}
