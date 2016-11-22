package com.rsw.gateway.controller;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAlms on 10/9/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerAggregateControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void securityConfiguration() throws Exception {
//        ResponseEntity<SecurityConfiguration> result
//                = restTemplate.getForEntity("/swagger-resources/configuration/security", SecurityConfiguration.class);
//        assertNotNull(result);
//        assertEquals(result, SecurityConfiguration.DEFAULT);
    }

    @Test
    public void uiConfiguration() throws Exception {

    }

    @Test
    public void swaggerResources() throws Exception {
    }

}