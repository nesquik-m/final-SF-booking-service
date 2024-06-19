package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Room;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.repository.RoomRepository;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Комната с ID {0} не найдена!", roomId)));
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long roomId, Room room) {
        Room updatedRoom = findRoomById(roomId);
        BeanUtils.copyNonNullProperties(room, updatedRoom);
        return roomRepository.save(updatedRoom);
    }

    @Override
    public void deleteRoomById(Long roomId) {
        findRoomById(roomId);
        roomRepository.deleteById(roomId);
    }

}
