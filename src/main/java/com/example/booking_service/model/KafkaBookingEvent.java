package com.example.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaBookingEvent {

    private Long userId;

    private LocalDate checkIn;

    private LocalDate checkOut;

}
