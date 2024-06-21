package com.example.booking_service.service;

import com.example.booking_service.entity.Room;
import com.example.booking_service.web.model.request.PageableRequest;
import com.example.booking_service.web.model.request.RoomFilter;
import org.springframework.data.domain.Page;

public interface RoomService {

    Page<Room> filterBy(RoomFilter filter, PageableRequest pageable);

    Room findRoomById(Long roomId);

    Room createRoom(Room room);

    Room updateRoom(Long roomId, Room room);

    void deleteRoomById(Long roomId);

}
