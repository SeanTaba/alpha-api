package com.revature.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class RootController
{
    @GetMapping
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public RedirectView redirectToApiDocumentation() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
