package com.example.booking_service.web.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    @Size(min = 3, max = 30, message = "Имя пользователя не может быть меньше {min} и больше {max} символов!")
    private String username;

    @NotBlank(message = "Пароль должен быть заполнен!")
    private String password;

    @NotBlank(message = "Email должен быть заполнен!")
    @Email(message = "Email указан некорректно!")
    private String email;

}
