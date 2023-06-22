package com.thbdesabase.orderservices.endpoint;

import com.thbdesabase.orderservices.dto.request.LoginRequest;
import com.thbdesabase.orderservices.dto.request.RegistrationRequest;
import com.thbdesabase.orderservices.dto.response.AuthenticationResponse;
import com.thbdesabase.orderservices.service.impl.AuthenticationServiceImpl;
import com.thbdesabase.orderservices.statval.IApplicationConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(IApplicationConstant.ContextPath.AUTHENTICATION)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl service;


    @PostMapping(IApplicationConstant.Path.Authentication.REGISTER)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping(IApplicationConstant.Path.Authentication.LOGIN)
   public ResponseEntity <AuthenticationResponse> login (@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
   }

   @PostMapping(IApplicationConstant.Path.Authentication.REFRESH_TOKEN)
   public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request,response);
   }

}
