package com.example.booking_service.repository;

import com.example.booking_service.entity.BookingStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsBookingRepository extends MongoRepository<BookingStatistics, Long> {

}
