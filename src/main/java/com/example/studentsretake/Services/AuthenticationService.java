package com.example.studentsretake.Services;

import com.example.studentsretake.dao.response.JwtAuthenticationResponse;
import com.example.studentsretake.dao.request.SignInRequest;
import com.example.studentsretake.dao.request.SignUpRequest;

public interface AuthenticationService {
    // Интерфейс AuthenticationService определяет методы для регистрации и входа в систему
    // с использованием JWT-аутентификации.

    // Метод для регистрации нового пользователя и выдачи JWT-токена в ответ.
    JwtAuthenticationResponse signUp(SignUpRequest request);

    // Метод для входа в систему с использованием JWT-токена и выдачи его в ответ.
    JwtAuthenticationResponse signIn(SignInRequest request);
}
