package com.example.booking_service.repository;

import com.example.booking_service.entity.Hotel;
import com.example.booking_service.web.model.request.HotelFilter;
import org.springframework.data.jpa.domain.Specification;

public interface HotelSpecification {

    static Specification<Hotel> withFilter(HotelFilter filter) {
        return Specification.where(byIdHotel(filter.getHotelId()))
                .and(byNameHotel(filter.getNameHotel()))
                .and(byTitle(filter.getTitle()))
                .and(byCity(filter.getCity()))
                .and(byAddress(filter.getAddress()))
                .and(byDistance(filter.getDistance()))
                .and(byRatingAndNumOfRatings(filter.getRating(), filter.getNumberOfRatings()));
    }

    static Specification<Hotel> byIdHotel(Long hotelId) {
        return (root, query, cb) -> {
            if (hotelId == null) {
                return null;
            }

            return cb.equal(root.get("id"), hotelId);
        };
    }

    static Specification<Hotel> byNameHotel(String nameHotel) {
        return (root, query, cb) -> {
            if (nameHotel == null) {
                return null;
            }

            return cb.equal(root.get("name"), nameHotel);
        };
    }

    static Specification<Hotel> byTitle(String title) {
        return (root, query, cb) -> {
            if (title == null) {
                return null;
            }

            return cb.equal(root.get("title"), title);
        };
    }

    static Specification<Hotel> byCity(String city) {
        return (root, query, cb) -> {
            if (city == null) {
                return null;
            }

            return cb.equal(root.get("city"), city);
        };
    }

    static Specification<Hotel> byAddress(String address) {
        return (root, query, cb) -> {
            if (address == null) {
                return null;
            }

            return cb.equal(root.get("address"), address);
        };
    }

    static Specification<Hotel> byDistance(Double distance) {
        return (root, query, cb) -> {
            if (distance == null) {
                return null;

            }

            return cb.lessThanOrEqualTo(root.get("distance"), distance);
        };
    }

    static Specification<Hotel> byRatingAndNumOfRatings(Double rating, Integer numberOfRatings) {
        return (root, query, cb) -> {
            if (rating == null && numberOfRatings == null) {
                return null;

            }

            if (rating == null) {
                return cb.greaterThanOrEqualTo(root.get("number_of_ratings"), numberOfRatings);
            }

            if (numberOfRatings == null) {
                return cb.greaterThanOrEqualTo(root.get("rating"), rating);
            }

            return cb.and(cb.greaterThanOrEqualTo(root.get("rating"), rating),
                    cb.greaterThanOrEqualTo(root.get("number_of_ratings"), numberOfRatings));
        };
    }

}
