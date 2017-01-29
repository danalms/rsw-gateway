package com.rsw.gateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by DAlms on 10/18/16.
 * In order to customize the OAuth2 SSO security settings, the @EnableOAuth2Sso annotation needs to be on
 * the WebSecurityConfigurerAdapter class/subclass.
 * Customization is being done here to disable CSRF since forms are not in use (may be common in REST API settings)
 *
 * About @EnableOAuthSso...
 * The SSO annotation should only be used when you want that server to trigger the authentication flow,
 * i.e. redirecting to the Auth Server for authorization and to get an OAuth token when needed.  Note that the
 * Spring SSO impl relies on its own JSESSIONID cookie to recognize the session.  Spring SSO sets this cookie, and
 * uses the cookie to lookup an OAuth2 token stored in the session, so there is no exposure of the actual token.
 * If your client application wants to interact with the auth server directly, and simply provide an OAuth2 token
 * to this server, then this server should be annotated with @EnableResourceServer and NOT with @EnableOAuthSso.
 * A Resource Server will validate the token, either by JWT signer verification or by implicit OAuth validation, where
 * user details are requested with the token using the configured security.oauth2.resource.userInfoUri property.
 * A Resource Server, if it finds no token, will just throw a 401 and will not attempt to redirect for authentication
 * even if the pertinent oauth client properties are present.
 * The @EnableOAuthSso annotation is actually an OAuth "client" with some additional capabilites to propagate the
 * token to downstream services.  The lower level @EnableOAuth2Client annotation enables the client functionality.
 *
 * About CSRF...
 * Spring Security will by default expect that a CSRF token is included in POST requests, even for API calls.
 * For this example (for simplicity), CSRF is being disabled.  To enable it for REST APIs, a Spring-provided filter can
 * be configured to set a generated token into the response header, but the REST client must provide it in POST request
 * header (X-CSRF-TOKEN).
 * Also note that disabling CSRF in the gateway is sufficient; it need not be explicitly disabled on the downstream
 * services in the Zuul proxy environment.
 *
 */
@Configuration
@EnableOAuth2Sso
@Profile(value = "sso")
public class SsoConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**")
            .authorizeRequests()
                .anyRequest().authenticated()
            .and().csrf().disable();
    }

}
