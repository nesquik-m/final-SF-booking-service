package com.example.booking_service.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    @NotBlank(message = "Название комнаты должно быть заполнено!")
    @Size(min = 3, max = 30, message = "Название комнаты не может быть меньше {min} и больше {max}!")
    private String name;

    @NotBlank(message = "Описание комнаты должно быть заполнено!")
    @Size(max = 500, message = "Описание комнаты не может быть больше {max} символов!")
    private String description;

    @NotNull(message = "Номер комнаты должен быть заполнен")
    @Positive
    private Integer roomNumber;

    @NotNull(message = "Цена за комнату должна быть заполнена")
    @Positive
    private BigDecimal price;

    @NotNull(message = "Максимальное количество людей, которое можно разместить в комнате, должно быть заполнено")
    @Positive
    private Integer maxNumberOfPeople;

    @NotNull(message = "Id отеля должен быть заполнен")
    @Positive
    private Long hotelId;

}
