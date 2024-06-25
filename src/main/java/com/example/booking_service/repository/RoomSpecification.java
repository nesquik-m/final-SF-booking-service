package com.example.booking_service.repository;

import com.example.booking_service.entity.Booking;
import com.example.booking_service.entity.Room;
import com.example.booking_service.web.model.request.RoomFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
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

    static Specification<Room> byRoomId(Long roomId) { // работает
        return (root, query, cb) -> {
            if (roomId == null) {
                return null;
            }

            return cb.equal(root.get("id"), roomId);
        };
    }

    static Specification<Room> byDescription(String description) { // работает
        return (root, query, cb) -> {
            if (description == null) {
                return null;
            }

            return cb.equal(root.get("description"), description);
        };
    }

    static Specification<Room> byCostRange(BigDecimal minPrice, BigDecimal maxPrice) { // работает
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

    static Specification<Room> byMaxNumberOfPeople(Integer maxNumberOfPeople) { // работает
        return (root, query, cb) -> {
            if (maxNumberOfPeople == null) {
                return null;
            }

            return cb.equal(root.get("maxNumberOfPeople"), maxNumberOfPeople);
        };
    }

    static Specification<Room> byPlacementDates(LocalDate checkIn, LocalDate checkOut) {
        return (root, query, cb) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }

            Join<Room, Booking> bookings = root.join("bookings", JoinType.LEFT);

            return cb.or(
                    cb.isNull(bookings.get("checkIn")),
                    cb.isNull(bookings.get("checkOut")),
                    cb.and(
                            cb.not(cb.between(bookings.get("checkIn"), checkIn, checkOut)),
                            cb.not(cb.between(bookings.get("checkOut"), checkIn, checkOut))));
        };
    }

    static Specification<Room> byHotelId(Long hotelId) { // TODO: при наличии в БД двух комнат с hotelId = 1, выдает только 1 объект
        return (root, query, cb) -> {
            if (hotelId == null) {
                return null;
            }

            return cb.equal(root.get("id"), hotelId);
        };
    }

}
