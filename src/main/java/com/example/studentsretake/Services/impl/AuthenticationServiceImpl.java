package com.example.studentsretake.Services.impl;

import com.example.studentsretake.Entities.User;
import com.example.studentsretake.Repos.UserRepo;
import com.example.studentsretake.Services.AuthenticationService;
import com.example.studentsretake.Services.JwtService;
import com.example.studentsretake.dao.response.JwtAuthenticationResponse;
import com.example.studentsretake.dao.request.SignInRequest;
import com.example.studentsretake.dao.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    // Метод для регистрации нового пользователя.
    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        // Создаем нового пользователя на основе данных из запроса.
        var user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .build();
        // Сохраняем пользователя в базе данных и присваиваем роль.
        user = userService.save(user, request.getRole());
        // Генерируем JWT-токен для пользователя.
        var jwt = jwtService.generateToken(user);
        // Возвращаем ответ с токеном.
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    // Метод для аутентификации пользователя.
    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        // Аутентификация пользователя с использованием Spring Security.
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // Поиск пользователя в базе данных по email.
        User user = userRepo.findUserByEmail(request.getEmail()).orElseThrow(() ->
            new UsernameNotFoundException(""));
        // Генерируем JWT-токен для пользователя.
        var jwt = jwtService.generateToken(user);
        // Возвращаем ответ с токеном.
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
