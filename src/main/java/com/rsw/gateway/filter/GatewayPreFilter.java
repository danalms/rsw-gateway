package com.rsw.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.rsw.gateway.context.SecurityUtils;
import com.rsw.gateway.domain.UserSession;
import com.rsw.gateway.repository.UserSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by DAlms on 2/19/17.
 */
public class GatewayPreFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayPreFilter.class);

    @Value("${gateway.security.session.timeoutSeconds:3600}")
    private long sessionTimeoutSeconds;

    @Autowired
    private UserSessionRepository userSessionRepository;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        Optional<String> sessionKey = SecurityUtils.currentUsername();
        if (! sessionKey.isPresent()) {
            return null;
        }

        UserSession session = userSessionRepository.findOne(sessionKey.get());
        LocalDateTime apiTime = LocalDateTime.now();
        LocalDateTime previousApiTime = null;
        String previousApiUri = null;
        if (session == null) {
            LOG.info("New session!");
            session = new UserSession(sessionKey.get());
            session.setSessionCreateTime(apiTime);
        } else {
            previousApiTime = session.getApiTime();
            previousApiUri = session.getApiUri();
            LOG.info("Idle time: {} ms.  Expired: {}", session.getIdleDurationMillis(),
                    session.isIdleLimitExceeded(1000L * sessionTimeoutSeconds));
        }
        session.setApiTime(apiTime);
        session.setApiUri(request.getRequestURI());


        LOG.info("Recording api activity, data={}, previousApiTime={}, previousApiUri={}", session, previousApiTime, previousApiUri);

        userSessionRepository.save(session);
        return null;
    }
}
