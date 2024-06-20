package com.example.booking_service.mapper;

import com.example.booking_service.entity.Room;
import com.example.booking_service.web.model.request.RoomRequest;
import com.example.booking_service.web.model.response.RoomResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room requestToRoom(RoomRequest request);

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomResponse roomToRoomResponse(Room room);

}
