package com.mike.foaast.adapters.repositories;

import com.google.common.net.HttpHeaders;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.mike.foaast.TestConstants.*;

public class DefaultMessageRepositoryTest {

    private MockWebServer mockWebServer;
    private DefaultMessageRepository instance;

    @BeforeEach
    public void setUp() {
        this.mockWebServer = new MockWebServer();
        final String baseUrl = mockWebServer.url(MESSAGES_SERVICE_BASE_URL).url().toString();
        this.instance = new DefaultMessageRepository(baseUrl);
    }

    @Test
    public void testRetrieveAwesomeMessage() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .setBody(VALID_AWESOME_MESSAGE)
        );

        Mono<String> result = this.instance.retrieveAwesomeMessage(VALID_FROM_NAME);

        StepVerifier.create(result)
                .expectNext(VALID_AWESOME_MESSAGE)
                .verifyComplete();
    }
}
