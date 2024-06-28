package com.example.booking_service.repository;

import com.example.booking_service.entity.Room;
import com.example.booking_service.entity.UnavailableDate;
import com.example.booking_service.web.model.request.RoomFilter;
import jakarta.persistence.criteria.*;
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

    static Specification<Room> byPlacementDates(LocalDate checkIn, LocalDate checkOut){
        return (root, query, cb) -> {
            if (checkIn == null || checkOut == null) {
                return null;
            }

            Subquery<UnavailableDate> subquery = query.subquery(UnavailableDate.class);
            Root<UnavailableDate> subRoot = subquery.from(UnavailableDate.class);
            subquery.select(subRoot.get("id"));

            Predicate roomMatch = cb.equal(subRoot.get("room"), root);
            Predicate dateOverlap = cb.between(subRoot.get("unavailableDate"), checkIn, checkOut);

            subquery.where(cb.and(roomMatch, dateOverlap));

            return cb.not(cb.exists(subquery));
        };
    }

    static Specification<Room> byHotelId(Long hotelId) {
        return (root, query, cb) -> {
            if (hotelId == null) {
                return null;
            }

            return cb.equal(root.get("hotel").get("id"), hotelId);
        };
    }

}
