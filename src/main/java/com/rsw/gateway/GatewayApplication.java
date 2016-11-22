package com.rsw.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Need to explicitly exclude SessionAutoConfiguration and import it conditionally based on cloud profile
 * Goal is to not use Redis and Spring Session when running locally - use only in cloud profile
 */
@EnableZuulProxy
@SpringBootApplication(exclude = SessionAutoConfiguration.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
