package com.revature.security;

import com.revature.models.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    private JwtConfig jwtConfig;

    @Autowired
    public TokenGenerator(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public String createJwt(User user){
        long now = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                                 .setId(Integer.toString(user.getId()))
                                 .setSubject(user.getUsername())
                                 .setIssuer("revature")
                                 .setIssuedAt((new Date(now)))
                                 .setExpiration(new Date(now+jwtConfig.getExpiration()))
                                 .signWith(jwtConfig.getSigAlg(),jwtConfig.getSigningKey());

        return jwtConfig.getPrefix() + builder.compact();
    }

    public JwtConfig getJwtConfig(){
        return  jwtConfig;
    }
}
