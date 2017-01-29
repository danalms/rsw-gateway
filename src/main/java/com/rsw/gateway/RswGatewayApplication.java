package com.rsw.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Need to explicitly exclude SessionAutoConfiguration and import it conditionally based on cloud profile
 * Goal is to not use Redis and Spring Session when running locally - use only in cloud profile
 */
@EnableZuulProxy
@SpringBootApplication(exclude = SessionAutoConfiguration.class)
public class RswGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RswGatewayApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
