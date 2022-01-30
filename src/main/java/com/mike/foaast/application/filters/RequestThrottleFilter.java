package com.mike.foaast.application.filters;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.mike.foaast.application.ApplicationConstants.*;

@Component
public class RequestThrottleFilter extends OncePerRequestFilter {
    private static final String MESSAGES_URL_PATH = "/api/v1/messages";

    private final Integer maxRequestsPerUser;
    private final LoadingCache<String, Integer> requestCountsPerUserId;

    public RequestThrottleFilter(@Value("${application.throttler.cacheExpiringAfterSecs}") Integer cacheExpiringAfterSecs,
                                 @Value("${application.throttler.maxRequestsPerUser}") Integer maxRequestsPerUser) {
        this.maxRequestsPerUser = maxRequestsPerUser;
        CacheLoader<String, Integer> loader;
        loader = new CacheLoader<>() {
            @Override
            public Integer load(String key) {
                return 0;
            }
        };
        this.requestCountsPerUserId = CacheBuilder.newBuilder()
                .expireAfterWrite(cacheExpiringAfterSecs, TimeUnit.SECONDS)
                .build(loader);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader(USER_ID_HEADER);

        if (userId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (this.isMaximumRequestsExceeded(userId)){
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write(TOO_MANY_REQUESTS_MESSAGE);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isMaximumRequestsExceeded(String userId) {
        Integer requests = requestCountsPerUserId.getIfPresent(userId);
        if(requests != null){
            if(requests >= this.maxRequestsPerUser) {
                return true;
            }
        } else {
            requests = 0;
        }
        requests++;
        requestCountsPerUserId.put(userId, requests);
        return false;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith(MESSAGES_URL_PATH);
    }
}
