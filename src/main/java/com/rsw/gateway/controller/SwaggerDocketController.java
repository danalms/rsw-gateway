package com.rsw.gateway.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DAlms on 12/19/16.
 *
 * This class acts as a "brute force" proxy forward for the /v2/api-docs Swagger docket info, i.e. bypasses
 * Zuul routing.
 * It's necessary to avoid having Swagger attach the proxy prefix (X-Forwarded-Prefix) that normally is sent downstream
 * in Zuul routing.  For the patient service the prefix would be "/swagger/patient".
 * When the X-Forwarded-Prefix is seen by Swagger, Swagger prepends the endpoint URI with the forwarded prefix,
 * resulting in something like "/swagger/patient/api/patient", which doesn't represent the real proxied URL we want
 * to make visible to the user in Swagger, e.g. just "/api/patient".
 */
@RestController
@RequestMapping("/swagger")
public class SwaggerDocketController {

    private Environment environment;

    @Value("${gateway.swagger.docketUri}")
    private String docketUri;

    @Value("${server.context-path:#{null}}")
    private String contextPath;

    private RestTemplate restTemplate;

    @Autowired
    public SwaggerDocketController(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @RequestMapping(value = "/{serviceName}", method = RequestMethod.GET)
    ResponseEntity<Object> getServiceDocker(@PathVariable("serviceName") String serviceName,
                                            HttpServletRequest request) throws Exception {

        // send this host as proxy since we want Swagger's URLs to be proxied
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Forwarded-Host", String.format("%s:%d", request.getServerName(), request.getServerPort()));
        if (StringUtils.isNotEmpty(contextPath) && ! "/".equals(contextPath)) {
            headers.set("X-Forwarded-Prefix", contextPath);
        }
        HttpEntity entity = new HttpEntity(headers);

        String serviceNameKey = String.format("gateway.services.%s", serviceName);
        String url = environment.getProperty(serviceNameKey) + docketUri;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

}
