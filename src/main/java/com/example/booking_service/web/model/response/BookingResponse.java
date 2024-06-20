package com.example.booking_service.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long roomId;

    private Long userId;

}
