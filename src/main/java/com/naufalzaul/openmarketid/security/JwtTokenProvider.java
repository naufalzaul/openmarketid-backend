package com.naufalzaul.openmarketid.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${assessment.hitachi.secretkey}")
    private String SECRET_KEY;
    @Value("${assessment.hitachi.expiration}")
    private Long EXPIRATION_TIME;

    public String getEmailFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //    public String getRoleFromToken(String token) {
    //        DecodedJWT decodedJWT = getDecodedJWT(token);
    //        return decodedJWT.getClaim("roles").asString();
    //        //        return roles.stream()

    /// /                .map(SimpleGrantedAuthority::new)
    /// /                .collect(Collectors.toList());
    //    }
    public List<GrantedAuthority> getRoleFromToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String generateToken(
            String email, Collection<? extends GrantedAuthority> auth
    ) {
        List<String> roles = auth.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(email)
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    private DecodedJWT getDecodedJWT(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token);
    }
}
