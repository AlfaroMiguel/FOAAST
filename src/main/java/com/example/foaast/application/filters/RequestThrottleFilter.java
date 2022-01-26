package com.example.foaast.application.filters;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RequestThrottleFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestThrottleFilter.class);

    private static final Integer EXPIRING_TIME = 10;
    private static final Integer MAX_REQUESTS = 5;

    private LoadingCache<String, Integer> requestCountsPerUserId;

    public RequestThrottleFilter() {
        CacheLoader<String, Integer> loader;
        loader = new CacheLoader<>() {
            @Override
            public Integer load(String key) {
                return 0;
            }
        };
        this.requestCountsPerUserId = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRING_TIME, TimeUnit.SECONDS)
                .build(loader);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader("userId");
        if (this.isMaximumRequestsExceeded(userId)){
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isMaximumRequestsExceeded(String userId) {
        Integer requests = requestCountsPerUserId.getIfPresent(userId);
        LOGGER.info("before requests: {}", requests);
        if(requests != null){
            if(requests > MAX_REQUESTS) {
                LOGGER.info("max requests reached");
                return true;
            }
        } else {
            requests = 0;
        }
        requests++;
        LOGGER.info("after requests: {}", requests);
        requestCountsPerUserId.put(userId, requests);
        return false;
    }
}
