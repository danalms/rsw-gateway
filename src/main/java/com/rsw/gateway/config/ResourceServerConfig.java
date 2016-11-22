package com.rsw.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


/**
 * Created by DAlms on 11/13/16.
 */
//@Configuration
//@EnableOAuth2Client
public class ResourceServerConfig {

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
