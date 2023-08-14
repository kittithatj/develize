package com.pim.develize.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pim.develize.entity.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    private Algorithm algorithm() {
        return Algorithm.HMAC256("SaltyKnight");
    }

    public String tokenize(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date expiresAt = calendar.getTime();

        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("principal", user.getUser_id())
                .withClaim("role", user.getRole())
                .withExpiresAt(expiresAt)
                .sign(this.algorithm());

        return token;
    }

    public DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(this.algorithm())
                    .withIssuer("auth0")
                    .build();
             return verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
    }

}
