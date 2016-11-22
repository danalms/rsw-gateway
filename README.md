# gateway-demo

Example of a minimally configured Netflix (Zuul) reverse proxy as implemented by Spring Security and Spring Boot

Key concepts being demonstrated are:

1. Reverse Proxy functionality in a microservice environment.  Currently, this server forwards only to the server
   defined in product-api-demo, a sample microservice stub
2. Composite Swagger API presentation, using the springfox Swagger implementation and a bit of custom code, the
   gateway hosts a list of Swagger API sets for the chosen proxied microservices.  This is accessible at
   localhost:8080/swagger-ui.html
3. OAuth2 security with both a Resource Server (oauth2 client) role, and an SSO role to ensure tokens are propagated to the
   downstream services
   1. As an OAuth2 client, there are three separate Spring profiles that invoke different configurations
      to demonstrate the following variations:
      1. profile = *github* :: Uses Github.com as the Authorization Server, using an authorized
         OAuth2 application on my Github account.  These are OAuth2 tokens.
      2. profile = *rossoauth2* :: Uses the auth-server-demo repo's Auth Server implementation, set for OAuth2 tokens
         See [auth-server-demo](https://github.com/danalms/auth-server-demo/blob/master/README.md) for details, as this 
         setting depends on the Auth Server's property settings for oauth2
      3. profile = *rossjwt* :: Uses the auth-server-demo repo's Auth Server implementation, set for JWT tokens
         See [auth-server-demo](https://github.com/danalms/auth-server-demo/blob/master/README.md) for details, as this 
         setting depends on the Auth Server's property settings for jwt
      4. Note that corresponding profile settings are required in the downstream product-api-demo service for
         everything to work properly
   2. As an SSO provider, tokens are provided to the downstream services with minimal configuration using Spring
      1. Downstream services are automatically provided with the Github OAuth2 token in the request header
      2. Downstream services use @EnableResourceServer to automatically setup security using the proxy-provided token
      3. The downstream Resource Server services, when using OAuth2 tokens, invoke the userInfoUri to fetch user
         details and set the Spring Security context.

The Github authentication produces a single Authority, ROLE_USER.
However, a minor customization of the AuthoritiesExtractor is provided as an example in the product-api-demo repo
ExternalOAuthConfig

