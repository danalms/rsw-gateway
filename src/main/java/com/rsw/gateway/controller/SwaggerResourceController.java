package com.rsw.gateway.controller;

import static com.google.common.base.Optional.fromNullable;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * This controller provides the Swagger UI with an aggregate of API docs from a customized list of microservices,
 * allowing the Gateway's API "Swagger" to serve as a proxy for a collection of microservice API docs.
 * This is achieved by implementing the standard Springfox Swagger endpoints, in particular /swagger-resources.
 * Note this approach works best if the Gateway itself does not provide its own Swagger Docket, i.e. does
 * not have its own APIs to represent.  The @EnableSwagger2 annotation used for API discovery will create these
 * endpoints in a Swagger-provided Servlet. Spring will not easily allow overriding them without more trickery.
 * Therefore, there is no SwaggerConfig with @EnableSwagger2 in the Gateway service.
 *
 * Reference:
 * https://groups.google.com/forum/#!searchin/swagger-swaggersocket/multiple/swagger-swaggersocket/g8fgSGUCrYs/A8Ms_lFOoN4J
 *
 */
@RestController
@ApiIgnore
@RequestMapping(value = "/swagger-resources")
public class SwaggerResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(SwaggerResourceController.class);

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    @Autowired(required = false)
    private UiConfiguration uiConfiguration;


    @RequestMapping(value = "/configuration/security")
    ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity<SecurityConfiguration>(
                fromNullable(securityConfiguration).or(SecurityConfiguration.DEFAULT), HttpStatus.OK);
    }

    @RequestMapping(value = "/configuration/ui")
    ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity<UiConfiguration>(
                fromNullable(uiConfiguration).or(UiConfiguration.DEFAULT), HttpStatus.OK);
    }

    /**
     * Add a SwaggerResource to the list for each separate microservice that has swagger endpoints
     * @param request
     * @return
     */
    @RequestMapping
    public ResponseEntity<List<SwaggerResource>> swaggerResources(HttpServletRequest request) {

        logRequest(request);

        List<SwaggerResource> result = Lists.newArrayList();
        result.add(swaggerResource("Product", "/swagger/product", "2.0"));
        result.add(swaggerResource("Token", "/swagger/token", "2.0"));

        return new ResponseEntity<List<SwaggerResource>>(result, HttpStatus.OK);
    }


    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource resource = new SwaggerResource();
        resource.setName(name);
        resource.setLocation(location);
        resource.setSwaggerVersion(version);
        return resource;
    }

    private void logRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            LOG.info("swaggerResources header: '{}' value: '{}'", headerName, request.getHeader(headerName));
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                LOG.info("swaggerResources cookie: '{}' domain: '{}' value: '{}'", cookie.getName(), cookie.getDomain(),
                        cookie.getValue());
            }
        }
    }
}
