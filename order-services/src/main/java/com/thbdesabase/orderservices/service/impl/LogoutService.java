package com.thbdesabase.orderservices.service.impl;

import com.thbdesabase.orderservices.dao.TokenDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenDao tokenDao;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        //check if the header is authentication
        //check if header is null or header do not start with "Bearer"
        //get the token from header
        //get the token from database
        // check if token persist
        //set expired and revoke to true

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith(" Bearer")) {
            return;
        }

        jwt = authHeader.substring(7);
        var storedToken = tokenDao.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenDao.save(storedToken);
            SecurityContextHolder.clearContext();
        }

    }

}
