package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.Room;
import com.example.booking_service.entity.UnavailableDate;
import com.example.booking_service.exception.AlreadyReservedDatesException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.model.KafkaBookingEvent;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.service.RoomService;
import com.example.booking_service.web.model.request.PageableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Value("${app.kafka.kafkaNewBookingTopic}")
    private String topicName;

    private final KafkaTemplate<String, KafkaBookingEvent> kafkaBookingEventTemplate;

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final RoomService roomService;

    @Override
    public List<Booking> findAllBookings(PageableRequest request) {
        return bookingRepository.findAll(request.pageRequest()).getContent();
    }

    @Override
    @Transactional
    public Booking bookARoom(Booking booking) {

        if (!isRoomAvailable(booking)) {
            throw new AlreadyReservedDatesException("Даты уже зарезервированы");
        }

        Booking createdBooking = bookingRepository.saveAndFlush(booking);
        kafkaBookingEventTemplate.send(topicName, bookingMapper.bookingToKafkaNewBooking(booking));

        return createdBooking;
    }

    private boolean isRoomAvailable(Booking booking) {
        Room room = booking.getRoom();
        List<LocalDate> requestedDates = getAllRequestedDates(booking.getCheckIn(), booking.getCheckOut());

        List<LocalDate> unavailableDates = room.getUnavailableDates().stream()
                .map(UnavailableDate::getUnavailableDate)
                .distinct()
                .toList();

        boolean hasIntersection = requestedDates.stream().anyMatch(unavailableDates::contains);

        if (hasIntersection) {
            return false;
        }

        requestedDates.forEach(date -> {
            UnavailableDate unavailable = UnavailableDate.builder()
                    .room(room)
                    .unavailableDate(date)
                    .build();
            room.getUnavailableDates().add(unavailable);
        });

        roomService.createRoom(room);
        return true;
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
