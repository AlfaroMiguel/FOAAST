package com.mike.foaast.application.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.mike.foaast.TestConstants.*;
import static com.mike.foaast.application.ApplicationConstants.TOO_MANY_REQUESTS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestThrottleFilterTest {

    private Integer cacheExpiringSecs;
    private Integer maxRequestsPerUser;
    private RequestThrottleFilter filter;

    @BeforeEach
    public void setUp() {
        this.cacheExpiringSecs = 5;
        this.maxRequestsPerUser = 1;
        this.filter = new RequestThrottleFilter(this.cacheExpiringSecs, this.maxRequestsPerUser);
    }

    @Test
    public void testWhenTheUserExceedsTheMaxAmountOfRequestsABadRequestIsReturned() throws ServletException, IOException {
        MockHttpServletRequest validRequest = mockValidRequest(VALID_USER_ID);
        MockHttpServletResponse validResponse = mockValidResponse();

        MockFilterChain chain = new MockFilterChain();

        // First request is processed as expected
        filter.doFilterInternal(validRequest, validResponse, chain);
        assertEquals(validResponse.getStatus(), HttpStatus.OK.value());
        assertEquals(validResponse.getContentAsString(), VALID_AWESOME_MESSAGE);

        MockHttpServletResponse emptyResponse = new MockHttpServletResponse();
        chain.reset();

        // The second request for the same user is filtered and a 429 (Too many request) is returned.
        filter.doFilterInternal(validRequest, emptyResponse, chain);
        assertEquals(emptyResponse.getStatus(), HttpStatus.TOO_MANY_REQUESTS.value());
        assertEquals(emptyResponse.getContentAsString(), TOO_MANY_REQUESTS_MESSAGE);
    }

    @Test
    public void testWhenTheUserExceedsTheMaxAmountOfRequestsButTheCacheExpiresAValidResponseIsReturned() throws ServletException, IOException, InterruptedException {
        MockHttpServletRequest validRequest = mockValidRequest(VALID_USER_ID);
        MockHttpServletResponse validResponse = mockValidResponse();

        MockFilterChain chain = new MockFilterChain();


        // First request is processed as expected
        filter.doFilterInternal(validRequest, validResponse, chain);
        assertEquals(validResponse.getStatus(), HttpStatus.OK.value());
        assertEquals(validResponse.getContentAsString(), VALID_AWESOME_MESSAGE);

        Thread.sleep(this.cacheExpiringSecs * 1000);
        chain.reset();

        // The second request for the same user is returned since the expiring time has passed.
        filter.doFilterInternal(validRequest, validResponse, chain);
        assertEquals(validResponse.getStatus(), HttpStatus.OK.value());
        assertEquals(validResponse.getContentAsString(), VALID_AWESOME_MESSAGE);
    }

    /**
     * Mocks a valid request.
     *
     * @return {@link MockHttpServletRequest}
     */
    private MockHttpServletRequest mockValidRequest(String userId) {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader(USER_ID_HEADER_NAME, userId);
        return req;
    }

    /**
     * Mocks a valid response.
     *
     * @return {@link MockHttpServletResponse}
     * @throws UnsupportedEncodingException
     */
    private MockHttpServletResponse mockValidResponse() throws UnsupportedEncodingException {
        MockHttpServletResponse res = new MockHttpServletResponse();
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(VALID_AWESOME_MESSAGE);
        return res;
    }
}
