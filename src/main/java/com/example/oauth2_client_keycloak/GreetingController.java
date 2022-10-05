package com.example.oauth2_client_keycloak;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/greet")
@PreAuthorize("isAuthenticated()")
public class GreetingController {

    @GetMapping()
    @PreAuthorize("hasAuthority('read')")
    public String getGreeting(JwtAuthenticationToken auth) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "Hi %s! You are granted with: %s."
                + auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME)
                + auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ", "[", "]"));

    }
}