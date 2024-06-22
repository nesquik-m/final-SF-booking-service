package com.example.booking_service.service.impl;

import com.example.booking_service.entity.BookingStatistics;
import com.example.booking_service.entity.UserStatistics;
import com.example.booking_service.repository.StatisticsBookingRepository;
import com.example.booking_service.repository.StatisticsUserRepository;
import com.example.booking_service.service.StatisticsService;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsUserRepository statisticsUserRepository;

    private final StatisticsBookingRepository statisticsBookingRepository;

    @Override
    public List<UserStatistics> findAllUsersData() {
        return statisticsUserRepository.findAll();
    }

    @Override
    public void saveUserData(UserStatistics userStatistics) {
        statisticsUserRepository.save(userStatistics);
    }

    @Override
    public List<BookingStatistics> findAllBookingData() {
        return statisticsBookingRepository.findAll();
    }

    @Override
    public void saveBookingData(BookingStatistics bookingStatistics) {
        statisticsBookingRepository.save(bookingStatistics);
    }

    @Override
    public void uploadStatistics() {

        try {
            FileWriter writer = new FileWriter("data/statistics.csv");

            StatefulBeanToCsv<UserStatistics> userCsvWriter = new StatefulBeanToCsvBuilder(writer).build();
            userCsvWriter.write(findAllUsersData());
            writer.flush();

            writer.write("\r\n");

            StatefulBeanToCsv<BookingStatistics> bookingCsvWriter = new StatefulBeanToCsvBuilder(writer).build();
            bookingCsvWriter.write(findAllBookingData());

            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

}
