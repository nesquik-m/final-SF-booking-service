package com.example.booking_service.web.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
public class PageableRequest {

    @NotNull(message = "Параметр 'pageSize' должен быть заполнен")
    @Positive
    private Integer pageSize;

    @NotNull(message = "Параметр 'pageNumber' должен быть заполнен")
    @PositiveOrZero
    private Integer pageNumber;

    public PageRequest pageRequest() {
        return PageRequest.of(pageNumber, pageSize);
    }

}
