package com.example.studentsretake.Services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    // Интерфейс JwtService определяет методы для работы с JWT-токенами.

    // Метод для извлечения имени пользователя (email) из JWT-токена.
    String extractUserName(String token);

    // Метод для генерации JWT-токена на основе UserDetails (информации о пользователе).
    String generateToken(UserDetails userDetails);

    // Метод для проверки валидности JWT-токена путем сравнения с данными пользователя (UserDetails).
    boolean isTokenValid(String token, UserDetails userDetails);
}
