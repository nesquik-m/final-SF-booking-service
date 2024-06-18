package com.example.booking_service.mapper;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.request.HotelRequest;
import com.example.booking_service.web.model.response.HotelResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(HotelMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel requestToHotel(HotelRequest request);

    HotelResponse hotelToHotelResponse(Hotel hotel);

}
