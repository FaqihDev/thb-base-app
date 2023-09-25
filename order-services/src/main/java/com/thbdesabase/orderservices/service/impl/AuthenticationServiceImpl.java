package com.thbdesabase.orderservices.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thbdesabase.orderservices.dao.AdminDao;
import com.thbdesabase.orderservices.dao.TokenDao;
import com.thbdesabase.orderservices.dto.request.LoginRequest;
import com.thbdesabase.orderservices.dto.request.RegistrationRequest;
import com.thbdesabase.orderservices.dto.response.AuthenticationResponse;
import com.thbdesabase.orderservices.entity.Admin;
import com.thbdesabase.orderservices.entity.Token;
import com.thbdesabase.orderservices.enumeration.ETokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final AdminDao adminDao;
    private final TokenDao tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest request) {
        var admin = Admin.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        var savedUser = adminDao.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        var refreshToken = jwtService.refreshToken(admin);
        saveUserToken(savedUser,jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }


    public AuthenticationResponse login (LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var admin = adminDao.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(admin);
        var refreshToken = jwtService.refreshToken(admin);
        revokeAllUserToken(admin);
        saveUserToken(admin,jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }



    private void saveUserToken(Admin admin, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .admin(admin)
                .tokenType(ETokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenDao.save(token);
    }

    private void revokeAllUserToken (Admin admin) {
        var validTokenUsers = tokenDao.findAllValidTokenByAdmin(admin.getId());
        if (validTokenUsers.isEmpty())
            return;

        validTokenUsers.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenDao.saveAll(validTokenUsers);
    }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;

        //check if header starts with bearer
        if (authHeader == null || !authHeader.startsWith(" Bearer")) {
            return;
        }

        //get refresh token from header
        refreshToken = authHeader.substring(7);

       //get user by token
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var admin = adminDao.findByEmail(userEmail).orElseThrow();

            //get valid token
            if (jwtService.isTokenValid(refreshToken,admin)) {
                var accessToken = jwtService.generateToken(admin);
                revokeAllUserToken(admin);
                saveUserToken(admin,accessToken);

                var authResponse = AuthenticationResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }



}
