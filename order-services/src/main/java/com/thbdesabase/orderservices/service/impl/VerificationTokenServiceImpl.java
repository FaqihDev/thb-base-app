package com.thbdesabase.orderservices.service.impl;

import com.thbdesabase.orderservices.dao.ITokenDao;
import com.thbdesabase.orderservices.entity.Token;
import com.thbdesabase.orderservices.service.IVerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements IVerificationTokenService {

   private final ITokenDao tokenDao;

    @Override
    public Token getTokenForUser(String token) {
        Optional<Token> tokenUser = tokenDao.findByToken(token);
        return tokenUser.orElse(null);
    }
}
