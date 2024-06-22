package com.example.booking_service.repository;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.Room;
import com.example.booking_service.web.model.request.RoomFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RoomSpecification {

    static Specification<Room> withFilter(RoomFilter filter) {
        return Specification.where(byRoomId(filter.getRoomId()))
                .and(byDescription(filter.getDescription()))
                .and(byCostRange(filter.getMinPrice(), filter.getMaxPrice()))
                .and(byMaxNumberOfPeople(filter.getMaxNumberOfPeople()))
                .and(byPlacementDates(filter.getCheckIn(), filter.getCheckOut()))
                .and(byHotelId(filter.getHotelId()));
    }

    static Specification<Room> byRoomId(Long roomId) {
        return (root, query, cb) -> {
            if (roomId == null) {
                return null;
            }

            return cb.equal(root.get("id"), roomId);
        };
    }

    static Specification<Room> byDescription(String description) {
        return (root, query, cb) -> {
            if (description == null) {
                return null;
            }

            return cb.equal(root.get("description"), description);
        };
    }

    static Specification<Room> byCostRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }

            if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            }

            if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            }

            return cb.between(root.get("price"), minPrice, maxPrice);
        };
    }

    static Specification<Room> byMaxNumberOfPeople(Integer maxNumberOfPeople) {
        return (root, query, cb) -> {
            if (maxNumberOfPeople == null) {
                return null;
            }

            return cb.equal(root.get("maxNumberOfPeople"), maxNumberOfPeople);
        };
    }

    static Specification<Room> byPlacementDates(LocalDate checkIn, LocalDate checkOut) { // TODO: не работает, доделать
        return (root, query, cb) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }

            // Объединение Room с Booking
            Join<Room, Booking> bookings = root.join("bookings", JoinType.LEFT);
            // Условия для исключения номеров с пересекающимися бронированиями
            return cb.and(
                    cb.or(
                            cb.isNull(bookings.get("checkIn")),
                            cb.isNull(bookings.get("checkOut")),
                            cb.lessThanOrEqualTo(bookings.get("checkOut"), checkIn),
                            cb.greaterThanOrEqualTo(bookings.get("checkIn"), checkOut)
                    )
            );
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, cb) -> {
            if (hotelId == null) {
                return null;
            }

            return cb.equal(root.get("id"), hotelId);
        };
    }

}
