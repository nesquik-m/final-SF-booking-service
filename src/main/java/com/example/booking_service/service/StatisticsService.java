package com.example.booking_service.service;

import com.example.booking_service.entity.BookingStatistics;
import com.example.booking_service.entity.UserStatistics;

import java.util.List;

public interface StatisticsService {

    List<UserStatistics> findAllUsersData();

    void saveUserData(UserStatistics userStatistics);

    List<BookingStatistics> findAllBookingData();

    void saveBookingData(BookingStatistics bookingStatistics);

    void uploadStatistics();

}
