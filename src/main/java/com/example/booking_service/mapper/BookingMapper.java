package com.example.booking_service.mapper;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.model.KafkaBookingEvent;
import com.example.booking_service.web.model.request.BookingRequest;
import com.example.booking_service.web.model.response.BookingResponse;
import com.example.booking_service.web.model.response.BookingResponseList;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    Booking requestToBooking(BookingRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    BookingResponse bookingToBookingResponse(Booking booking);

    default BookingResponseList bookingListToBookingResponseList(List<Booking> bookings) {
        return BookingResponseList.builder()
                .bookings(bookings.stream().map(this::bookingToBookingResponse).collect(Collectors.toList()))
                .build();
    }

    @Mapping(source = "user.id", target = "userId")
    KafkaBookingEvent bookingToKafkaNewBooking(Booking booking);

}
