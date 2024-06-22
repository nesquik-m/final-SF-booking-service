package com.example.booking_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class BookingStatistics {

    @Id
    private String id;

    private Long userId;

    private LocalDate checkIn;

    private LocalDate checkOut;

}
