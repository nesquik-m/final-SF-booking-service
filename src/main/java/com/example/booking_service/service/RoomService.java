package com.example.booking_service.service;

import com.example.booking_service.entity.Room;

public interface RoomService {

    Room findRoomById(Long roomId);

    Room createRoom(Room room);

    Room updateRoom(Long roomId, Room room);

    void deleteRoomById(Long roomId);

}
