package com.thbdesabase.orderservices.service;

import com.thbdesabase.orderservices.entity.Token;

public interface IVerificationTokenService {

    Token getTokenForUser(String token);
}
