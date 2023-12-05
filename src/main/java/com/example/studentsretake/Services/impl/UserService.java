package com.example.studentsretake.Services.impl;

import com.example.studentsretake.Entities.Role;
import com.example.studentsretake.Entities.User;
import com.example.studentsretake.Repos.RoleRepo;
import com.example.studentsretake.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для сохранения нового пользователя
    public boolean saveNewUser(User user) {
        // Проверяем, существует ли пользователь с таким email в базе данных
        User userFromBd = userRepo.findUserByEmail(user.getEmail()).orElse(null);
        if (userFromBd == null) {
            // Если пользователя нет, создаем роль "student", хешируем пароль и сохраняем пользователя в базу данных
            Role studentRole = roleRepo.findById(1L).orElse(null);
            user.setRoles(Collections.singleton(studentRole));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return true; // Возвращаем true, если пользователь успешно создан
        } else {
            return false; // Возвращаем false, если пользователь с таким email уже существует
        }
    }

    // Метод для сохранения пользователя с указанной ролью
    public User save(User user, String roleName) {
        // Создаем новую роль, сохраняем ее в базу данных, устанавливаем ее для пользователя и сохраняем пользователя
        Role role = Role.builder().name(roleName).build();
        role = roleRepo.save(role);
        user.setRoles(Collections.singleton(role));
        user.setPassword(user.getPassword());
        return userRepo.save(user);
    }

    // Метод для поиска пользователя по email
    public UserDetails findUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    // Метод для получения пользователя по email
    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    // Метод для замены существующего пользователя
    public User replaceUser(User newUser, Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(newUser.getEmail()));
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepo.save(user);
    }

    // Метод для получения пользователя из базы данных по id
    public User getUserFromBd(Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
