package com.revature.aspects;

import com.revature.dtos.PrincipalDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.models.Role;
import com.revature.security.JwtConfig;
import com.revature.security.Secured;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Aspect
@Component
public class SecurityAspect {

    private Logger logger = LogManager.getLogger(SecurityAspect.class);
    private JwtConfig jwtConfig;

    @Autowired
    public SecurityAspect(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Around("@annotation(com.revature.security.Secured)")
    public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable {

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured securedAnno = method.getAnnotation(Secured.class);
        List<Role> allowedRoles = Arrays.asList(securedAnno.allowedRoles());

        HttpServletRequest req = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        PrincipalDTO principal = parseToken(req).orElseThrow(() -> new AuthenticationException("An unauthenticated request was made to a protected endpoint!"));
// Not used currently this is if we implement roles
//        if (!allowedRoles.contains(principal.getRole().toString())) {
//            throw new AuthorizationException("A forbidden request was made by: " + principal.getUsername());
//        }

        return pjp.proceed();

    }

    protected Optional<PrincipalDTO> parseToken(HttpServletRequest req) {

        try {

            String header = req.getHeader(jwtConfig.getHeader());

            if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
                logger.info("Request originates from an unauthenticated source.");
                return Optional.empty();
            }

            String token = header.replaceAll(jwtConfig.getPrefix(), "");

            Claims jwtClaims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();

            return Optional.of(new PrincipalDTO(jwtClaims));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }

    }
}
