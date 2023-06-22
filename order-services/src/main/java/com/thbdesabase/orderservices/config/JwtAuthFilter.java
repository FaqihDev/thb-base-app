package com.thbdesabase.orderservices.config;

import com.thbdesabase.orderservices.dao.TokenDao;
import com.thbdesabase.orderservices.service.impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final TokenDao tokenDao;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                                      @NonNull HttpServletResponse response,
                                                      @NonNull FilterChain filterChain) throws ServletException, IOException {


        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request,response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final  String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith(" Bearer")) {
            filterChain.doFilter(request,response);
        }

        if (Objects.nonNull(authHeader)) {
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            //this is to check for new user
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)  {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                var isTokenValid = tokenDao.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);

                //re-check again if token is valid
                if (jwtService.isTokenValid(jwt,userDetails) && Boolean.TRUE.equals(isTokenValid)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}