package com.example.booking_service.mapper;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.request.HotelRequest;
import com.example.booking_service.web.model.response.HotelResponse;
import com.example.booking_service.web.model.response.HotelResponseList;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(HotelMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(HotelRequest request);

    HotelResponse hotelToHotelResponse(Hotel hotel);

    default HotelResponseList hotelListToHotelResponseList(List<Hotel> hotels) {
        return HotelResponseList.builder()
                .hotels(hotels.stream().map(this::hotelToHotelResponse).collect(Collectors.toList()))
                .build();
    }

}
