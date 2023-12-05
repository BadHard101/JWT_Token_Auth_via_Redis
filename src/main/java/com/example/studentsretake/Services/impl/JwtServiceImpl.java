package com.example.studentsretake.Services.impl;

import com.example.studentsretake.Services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    // Извлекает имя пользователя из JWT-токена.
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Извлекает утверждение (claim) из JWT-токена, используя переданную функцию.
    private <T> T extractClaim(String token,  Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Извлекает все утверждения (claims) из JWT-токена.
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getPayload();
    }

    // Получает ключ для подписи JWT из настроек.
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Генерирует JWT на основе переданных утверждений и UserDetails.
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Генерирует JWT с заданными утверждениями и дополнительными параметрами.
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        log.debug("\n" + userDetails.getUsername() + "\n");
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // JWT действителен в течение 24 часов
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    // Проверяет валидность JWT-токена. Проверяет, что токен не истек и соответствует переданным данным пользователя.
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Проверяет, истек ли срок действия JWT-токена.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Извлекает дату истечения срока действия токена из JWT.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
