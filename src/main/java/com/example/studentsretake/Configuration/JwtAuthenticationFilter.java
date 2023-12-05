package com.example.studentsretake.Configuration;

import com.example.studentsretake.Services.JwtService;
import com.example.studentsretake.Services.impl.UserDetailsService;
import com.example.studentsretake.Services.impl.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//реализуем логику токенов
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Считываем хэдер авторизации
        final String jwt;
        final String userEmail;

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response); // Если хэдер отсутствует или не начинается с "Bearer", пропускаем запрос дальше
            return;
        }

        jwt = authHeader.substring(7); // Извлекаем JWT токен, убирая "Bearer "
        userEmail = jwtService.extractUserName(jwt); // Извлекаем email пользователя из токена

        log.debug(userEmail); // Выводим email в логи для отладки

        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail); // Загружаем UserDetails по email

            if (jwtService.isTokenValid(jwt, userDetails)) { // Проверяем валидность токена
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context); // Устанавливаем аутентификацию в SecurityContextHolder
            }
        }

        filterChain.doFilter(request, response); // Передаём запрос дальше в цепочку фильтров
    }
}
