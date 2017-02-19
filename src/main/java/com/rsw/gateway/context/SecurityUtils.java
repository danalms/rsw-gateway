package com.rsw.gateway.context;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Optional;

/**
 * Created by DAlms on 1/26/17.
 */
public abstract class SecurityUtils {

    public static Optional<String> currentUserAsToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getDetails() instanceof OAuth2AuthenticationDetails)) {
            return Optional.empty();
        }
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();

        return Optional.ofNullable(details.getTokenValue());
    }

    public static Optional<Jwt> currentUserAsJwt() {
        Optional<String> token = currentUserAsToken();
        if (! token.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(JwtHelper.decode(token.get()));
    }

    public static Optional<String> currentUsername() {
        Optional<Jwt> jwt = currentUserAsJwt();
        if (! jwt.isPresent()) {
            return Optional.empty();
        }
        JSONObject jsonObject = new JSONObject(jwt.get().getClaims());
        return Optional.ofNullable(jsonObject.getString("username"));
    }

}
