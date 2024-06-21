package com.example.booking_service.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseList {

    private Long totalCountHotels;

    @Builder.Default
    private List<HotelResponse> hotels = new ArrayList<>();

}
