package com.revature.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return Jwts.parser().setSigningKey(jwtConfig.getSigningKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws ExpiredJwtException {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSigningKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
        } catch (MalformedJwtException e) {
        } catch (UnsupportedJwtException e) {
        } catch (IllegalArgumentException e) {
        }

        return false;
    }

    public String refreshJwtToken(ExpiredJwtException authToken){
        return tokenGenerator.refreshJwt(authToken);
    }
}
