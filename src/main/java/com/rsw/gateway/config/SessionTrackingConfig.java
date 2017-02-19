package com.rsw.gateway.config;

import com.rsw.gateway.filter.GatewayPreFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * Created by DAlms on 11/20/16.
 */
@Configuration
@ConditionalOnProperty(value = "gateway.sessionTracking.enabled", matchIfMissing = false)
@EnableRedisRepositories
public class SessionTrackingConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SessionTrackingConfig.class);

    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public GatewayPreFilter gatewayPreFilter() {
        return new GatewayPreFilter();
    }

}
