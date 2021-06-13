package com.revature.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtility {

    private JwtConfig jwtConfig;
    private TokenGenerator tokenGenerator;

    @Autowired
    public JwtUtility(JwtConfig jwtConfig, TokenGenerator tokenGenerator){
        this.jwtConfig = jwtConfig;
        this.tokenGenerator = tokenGenerator;
    }

    public String getUserNameFromJwtToken(String token) {
        // Needed to be explicit because when chained together couldnt see JWT token
        JwtParser parser = Jwts.parser();
        token = token.replaceFirst(": Bearer ","");
        Key signingKey = jwtConfig.getSigningKey();
        parser.setSigningKey(signingKey);
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws ExpiredJwtException {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSigningKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | IllegalArgumentException | UnsupportedJwtException | MalformedJwtException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String refreshJwtToken(ExpiredJwtException authToken){
        return tokenGenerator.refreshJwt(authToken);
    }
}
