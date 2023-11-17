package com.thbdesabase.orderservices.endpoint;

import com.thbdesabase.orderservices.dto.request.LoginRequest;
import com.thbdesabase.orderservices.dto.request.RegistrationRequest;
import com.thbdesabase.orderservices.dto.response.AuthenticationResponse;
import com.thbdesabase.orderservices.dto.response.HttpResponse;
import com.thbdesabase.orderservices.entity.Admin;
import com.thbdesabase.orderservices.entity.Token;
import com.thbdesabase.orderservices.service.impl.AuthenticationServiceImpl;
import com.thbdesabase.orderservices.service.impl.JwtService;
import com.thbdesabase.orderservices.service.impl.VerificationTokenServiceImpl;
import com.thbdesabase.orderservices.statval.IApplicationConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;


@RestController
@RequestMapping(IApplicationConstant.ContextPath.AUTHENTICATION)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;
    private final JwtService jwtService;
    @PostMapping(IApplicationConstant.Path.Authentication.REGISTER)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request){
        Admin admin = authenticationService.register(request);
        String token = jwtService.generateToken(admin);
        String refreshToken = jwtService.refreshToken(admin);
        AuthenticationResponse authenticationResponse= AuthenticationResponse.builder()
                .name(admin.getFirstName() + " " + admin.getLastName())
                .email(admin.getEmail())
                .isEnabled(admin.getIsEnabled())
                .role(admin.getRole())
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .developerMessage("Please login to your account")
                        .message("User created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .data(authenticationResponse)
                        .build());
    }

    @GetMapping(IApplicationConstant.Path.Authentication.LOGIN)
    public ResponseEntity <AuthenticationResponse> login (@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }


    @GetMapping
    public ResponseEntity<?> verifyEmail (@RequestParam("token") String token){
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .message("Account verified successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

   @PostMapping(IApplicationConstant.Path.Authentication.REFRESH_TOKEN)
   public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
       authenticationService.refreshToken(request,response);
   }

}
