package com.example.booking_service.service.impl;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.exception.AlreadyReservedDatesException;
import com.example.booking_service.mapper.BookingMapper;
import com.example.booking_service.model.KafkaBookingEvent;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.service.BookingService;
import com.example.booking_service.web.model.request.PageableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Booking> findAllBookings(PageableRequest request) {
        return bookingRepository.findAll(request.pageRequest()).getContent();
    }

    @Override
    public Booking bookARoom(Booking booking) {

        if (!isRoomAvailable(booking)) {
            throw new AlreadyReservedDatesException("Даты уже зарезервированы");
        }

        Booking createdBooking = bookingRepository.saveAndFlush(booking);
        kafkaBookingEventTemplate.send(topicName, bookingMapper.bookingToKafkaNewBooking(booking));

        return createdBooking;
    }

    private boolean isRoomAvailable(Booking booking) {
        List<Booking> bookings = booking.getRoom().getBookings();

        for (Booking book : bookings) {
            if (!book.getCheckIn().isAfter(booking.getCheckIn()) && !booking.getCheckIn().isAfter(book.getCheckOut()) ||
                    !book.getCheckIn().isAfter(booking.getCheckOut()) && !booking.getCheckOut().isAfter(book.getCheckOut())) {
                return false;
            }
        }

        return true;
    }

}
