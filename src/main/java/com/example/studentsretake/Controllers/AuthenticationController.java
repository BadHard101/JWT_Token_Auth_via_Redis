package com.example.studentsretake.Controllers;

import com.example.studentsretake.Entities.RedisEntity;
import com.example.studentsretake.Services.AuthenticationService;
import com.example.studentsretake.dao.RedisEntityDao;
import com.example.studentsretake.dao.response.JwtAuthenticationResponse;
import com.example.studentsretake.dao.request.SignInRequest;
import com.example.studentsretake.dao.request.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private  AuthenticationService authenticationService;
    @Autowired
    private RedisEntityDao redisEntityDao;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> singUp(@RequestBody SignUpRequest request, Principal principal){
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        redisEntityDao.save(RedisEntity.builder().userEmail(request.getEmail()).token(response.getToken()).build());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> singIn(@RequestBody SignInRequest request){
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        redisEntityDao.save(RedisEntity.builder().userEmail(request.getEmail()).token(response.getToken()).build());
        return ResponseEntity.ok(response);
    }
}
