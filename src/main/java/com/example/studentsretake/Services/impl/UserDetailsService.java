package com.example.studentsretake.Services.impl;

import com.example.studentsretake.Entities.User;
import com.example.studentsretake.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    // Метод для загрузки пользователя по его email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Ищем пользователя в репозитории по email, если не найден, выбрасываем исключение
        User user = userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        // Возвращаем объект пользователя, преобразованный в UserDetails
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .authorities(user.getAuthorities())
                .password(user.getPassword())
                .build();
    }
}
