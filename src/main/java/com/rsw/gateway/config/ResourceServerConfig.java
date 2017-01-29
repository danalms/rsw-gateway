package com.rsw.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * Created by DAlms on 11/13/16.
 */
@Configuration
//@EnableOAuth2Client
@EnableResourceServer
@Profile(value = "nosso")
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    /**
     * permit swagger UI and reveal of the API contract for unauthenticated users
     * invoking the API still requires an oauth2 token of course
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/v2/api-docs", "/swagger/**", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/util/**");
    }

    /**
     * this is mostly redundant with default behavior, but explicitly disabling csrf here temporarily
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    // Experimented with trying to dictate what grant type is to be used between the gateway as resource server,
    // and the auth server - when running in SSO mode. The comments below are in relation to that...
    //
    // This isn't enough to override the type of resource details to alter the grant type being requested
    // by the SSO Resource Server...
    // Either need to extend/override UserInfoRestTemplateFactory (as I began doing), or back away from
    // @EnableOAuthSso and use the lower level @EnableOAuth2Client annotation instead, which would allow a simple
    // definition of the RestTemplate such as below, BUT will require wiring up the other filter stuff to
    // incorporate the SSO aspect.
    // http://stackoverflow.com/questions/37534073/spring-boot-oauth2-client-credentials

//    @Bean
//    public OAuth2RestTemplate userInfoRestTemplate() {

    // implicit
//        return new OAuth2RestTemplate(new ImplicitResourceDetails(), new DefaultOAuth2ClientContext());


    // client credentials
//        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
//        resourceDetails.setAccessTokenUri("");
//        resourceDetails.setClientId("rsw");
//        resourceDetails.setClientSecret("rswsecret");
//        resourceDetails.setScope(Arrays.asList("read","write"));
//        return new OAuth2RestTemplate(new ClientCredentialsResourceDetails());

//    }

}
