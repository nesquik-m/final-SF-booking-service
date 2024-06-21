package com.example.booking_service.repository;

import com.example.booking_service.entity.Room;
import com.example.booking_service.web.model.request.RoomFilter;
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

    static Specification<Room> byPlacementDates(LocalDate checkIn, LocalDate checkOut) {
        return (root, query, cb) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }
            // метод должен при выборе дат заезда и выезда показывать только те номера, которые свободны в этом временном диапазоне

            return null;
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
