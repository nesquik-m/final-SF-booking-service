package com.example.booking_service.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {

    @NotBlank(message = "Название отеля должно быть заполнено!")
    @Size(min = 3, max = 30, message = "Название отеля не может быть меньше {min} и больше {max}!")
    private String name;

    @NotBlank(message = " Заголовок объявления должен быть заполнен!")
    private String title;

    @NotBlank(message = "Город должен быть заполнен!")
    private String city;

    @NotBlank(message = "Адрес должен быть заполнен!")
    private String address;

    @NotNull(message = "Расстояние от центра города должно быть заполнено!")
    @Positive(message = "Расстояние от центра города должно быть больше 0")
    private Double distance;

}
