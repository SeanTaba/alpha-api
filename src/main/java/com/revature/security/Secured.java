package com.revature.security;

import com.revature.models.Role;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    Role[] allowedRoles();
}
