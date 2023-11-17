package com.thbdesabase.orderservices.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thbdesabase.orderservices.dao.AdminDao;
import com.thbdesabase.orderservices.dao.ITokenDao;
import com.thbdesabase.orderservices.dto.request.LoginRequest;
import com.thbdesabase.orderservices.dto.request.RegistrationRequest;
import com.thbdesabase.orderservices.dto.response.AuthenticationResponse;
import com.thbdesabase.orderservices.entity.Admin;
import com.thbdesabase.orderservices.entity.Token;
import com.thbdesabase.orderservices.enumeration.ETokenType;
import com.thbdesabase.orderservices.exception.EmailAlreadyVerifiedException;
import com.thbdesabase.orderservices.exception.InvalidDataException;
import com.thbdesabase.orderservices.exception.UserAlreadyExistException;
import com.thbdesabase.orderservices.service.IEmailService;
import com.thbdesabase.orderservices.service.IVerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.thbdesabase.orderservices.util.EmailUtils.isValidFormatEmail;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationServiceImpl {

    private final AdminDao adminDao;
    private final IVerificationTokenService tokenService;
    private final ITokenDao tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IEmailService emailService;


    public Admin register(RegistrationRequest request) {

        Optional<Admin> admin = adminDao.findByEmail(request.getEmail());
        if (admin.isPresent()) {
            throw new UserAlreadyExistException(401,"User with email " + request.getEmail() + "is already exist");
        }

        if (!isValidFormatEmail(request.getEmail())) {
            throw new InvalidDataException(401,"Email format is invalid, please use valid gmail format");
        }

        var theAdmin = Admin.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role("USER")
                .isEnabled(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Admin savedAdmin = adminDao.save(theAdmin);
        var jwtToken = jwtService.generateToken(savedAdmin);
        var refreshToken = jwtService.refreshToken(savedAdmin);
        saveUserToken(savedAdmin,jwtToken);
        emailService.sendSimpleMailMessage(theAdmin.getFirstName(),theAdmin.getEmail(),jwtToken);
        log.info("sending email verification to {} ",theAdmin.getEmail());
        return savedAdmin;
    }

    public String verifyEmail (String token) {
        var tokenUser = tokenService.getTokenForUser(token);
        if (tokenUser.getAdmin().getIsEnabled()) {
            throw new EmailAlreadyVerifiedException(401,"Email is already verified, please Login to your account");
        }

        if (this.validateToken(token).equalsIgnoreCase("valid")) {
           return "email verified successfully, please login !";
        }
        return "Invalid token, failed to verify email";
    }


    public String validateToken(String token) {
        var tokenUser = tokenService.getTokenForUser(token);
        if (Objects.isNull(tokenUser)) {
            throw new InvalidDataException(401,"Token is null, invalid !");
        }

        var admin = tokenUser.getAdmin();
        if (tokenUser.isExpired) {
            throw new InvalidDataException(401,"Token is already expired !");
        }

        admin.setIsEnabled(true);
        adminDao.save(admin);
        return "Valid";
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
