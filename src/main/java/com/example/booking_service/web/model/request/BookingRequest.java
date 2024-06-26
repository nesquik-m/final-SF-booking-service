package com.example.booking_service.web.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @Future(message = "Некорректный ввод даты заезда")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @Future(message = "Некорректный ввод даты выезда")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;

    @NotNull(message = "Id комнаты должен быть заполнен")
    @Positive
    private Long roomId;

    @NotNull(message = "Id пользователя должен быть заполнен")
    @Positive
    private Long userId;

}
