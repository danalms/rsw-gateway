package com.rsw.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by DAlms on 11/20/16.
 */
@Profile(value = "cloud")
@EnableRedisHttpSession
@Import(SessionAutoConfiguration.class)
public class HttpSessionConfig {

    private static final Logger LOG = LoggerFactory.getLogger(HttpSessionConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        LOG.info("##### JedisConnectionFactory redisHost={}, redisPort={}", redisHost, redisPort);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(redisHost);
        connectionFactory.setPort(redisPort);
        return connectionFactory;
    }
}
