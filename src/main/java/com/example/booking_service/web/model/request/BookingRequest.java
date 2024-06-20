package com.example.booking_service.web.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Дата заезда должна быть заполнена!")
    @Future(message = "Некорректный ввод даты заезда")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;

    @NotBlank(message = "Дата выезда должна быть заполнена!")
    @Future(message = "Некорректный ввод даты выезда")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;

    @NotNull(message = "Id комнаты должен быть заполнен")
    @Positive
    private Long roomId;

    @NotNull(message = "Id пользователя должен быть заполнен")
    @Positive
    private Long userId;

}
