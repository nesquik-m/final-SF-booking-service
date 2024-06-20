package com.example.booking_service.web.controller;

import com.example.booking_service.entity.Room;
import com.example.booking_service.mapper.RoomMapper;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.web.model.request.RoomRequest;
import com.example.booking_service.web.model.response.RoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final RoomMapper roomMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> findRoomById(@PathVariable("id") Long roomId) {
        return ResponseEntity.ok().body(roomMapper.roomToRoomResponse(roomService.findRoomById(roomId)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody @Valid RoomRequest request) {
        Room createdRoom = roomService.createRoom(roomMapper.requestToRoom(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(roomMapper.roomToRoomResponse(createdRoom));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable("id") Long roomId,
                                                     @RequestBody @Valid RoomRequest request) {
        Room updatedRoom = roomService.updateRoom(roomId, roomMapper.requestToRoom(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(roomMapper.roomToRoomResponse(updatedRoom));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRoomById(@PathVariable("id") Long roomId) {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
