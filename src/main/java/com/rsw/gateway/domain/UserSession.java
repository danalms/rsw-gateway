package com.rsw.gateway.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * Created by DAlms on 2/12/17.
 */
@RedisHash("usersession")
public class UserSession {

    @Id
    private String id;

    private LocalDateTime sessionCreateTime;
    private LocalDateTime apiTime;
    private String apiUri;

    public UserSession() {
    }

    public UserSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public UserSession setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getSessionCreateTime() {
        return sessionCreateTime;
    }

    public UserSession setSessionCreateTime(LocalDateTime sessionCreateTime) {
        this.sessionCreateTime = sessionCreateTime;
        return this;
    }

    public LocalDateTime getApiTime() {
        return apiTime;
    }

    public UserSession setApiTime(LocalDateTime apiTime) {
        this.apiTime = apiTime;
        return this;
    }

    public String getApiUri() {
        return apiUri;
    }

    public UserSession setApiUri(String apiUri) {
        this.apiUri = apiUri;
        return this;
    }

    public Optional<Long> getIdleDurationMillis() {
        if (apiTime == null) {
            return Optional.empty();
        }
        LocalDateTime currentTime = LocalDateTime.now();

        Long deltaMillis = Duration.between(apiTime, currentTime).toMillis();
        return Optional.of(deltaMillis);
    }

    public Optional<Boolean> isIdleLimitExceeded(Long limitValueMillis) {
        Optional<Long> idleDuration = getIdleDurationMillis();
        if (! idleDuration.isPresent()) {
            return Optional.empty();
        }
        if (idleDuration.get().compareTo(limitValueMillis) >=0) {
            return Optional.of(Boolean.TRUE);
        } else {
            return Optional.of(Boolean.FALSE);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("sessionCreateTime", sessionCreateTime)
                .append("apiTime", apiTime)
                .append("apiUri", apiUri)
                .toString();
    }
}