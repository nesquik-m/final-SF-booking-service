package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.Room;
import com.example.booking_service.exception.AlreadyReservedDatesException;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.web.model.request.PageableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final RoomService roomService;

    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> findAllBookings(PageableRequest request) {
        return bookingRepository.findAll(request.pageRequest()).getContent();
    }

    @Override
    public Booking bookARoom(Booking booking) {
        List<LocalDate> dates = getAllRequestedDates(booking.getCheckIn(), booking.getCheckOut());
        Room room = booking.getRoom();
        List<LocalDate> bookedDates = room.getBookedDates();

        boolean hasCommonDate = bookedDates.stream().anyMatch(date -> dates.stream().anyMatch(d -> Objects.equals(date, d)));

        if (hasCommonDate) {
            throw new AlreadyReservedDatesException("Даты уже зарезервированы");
        }

        room.addBookedDates(dates);
        roomService.createRoom(room);

        return bookingRepository.saveAndFlush(booking);
    }

    private List<LocalDate> getAllRequestedDates(LocalDate checkIn, LocalDate checkOut) {
        List<LocalDate> dates = new ArrayList<>();

        while (!checkIn.isAfter(checkOut)) {
            dates.add(checkIn);
            checkIn = checkIn.plusDays(1);
        }

        return dates;
    }

}
