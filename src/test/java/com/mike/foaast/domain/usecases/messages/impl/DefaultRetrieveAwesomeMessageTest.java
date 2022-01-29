package com.mike.foaast.domain.usecases.messages.impl;

import com.google.common.base.VerifyException;
import com.mike.foaast.domain.repositories.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

import static com.mike.foaast.TestConstants.*;
import static com.mike.foaast.domain.ServiceConstants.IS_REQUIRED_TEMPLATE;
import static com.mike.foaast.domain.ServiceConstants.MESSAGE_FROM_VALUE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class DefaultRetrieveAwesomeMessageTest {

    private MessageRepository messageRepository;
    private DefaultRetrieveAwesomeMessage instance;

    @BeforeEach
    public void setUp() {
        this.messageRepository = mock(MessageRepository.class);
        this.instance = new DefaultRetrieveAwesomeMessage(this.messageRepository);
    }

    @Test
    public void testRetrieveAwesomeMessage() {
        PublisherProbe<String> messagePublisherProbe = PublisherProbe.of(Mono.just(VALID_AWESOME_MESSAGE));

        when(this.messageRepository.retrieveAwesomeMessage(VALID_FROM_NAME))
                .thenReturn(messagePublisherProbe.mono());

        Mono<String> result = this.instance.retrieve(VALID_FROM_NAME);

        StepVerifier.create(result)
                .expectNext(VALID_AWESOME_MESSAGE)
                .verifyComplete();

        assertThat(messagePublisherProbe.wasSubscribed()).isTrue();
        verify(this.messageRepository).retrieveAwesomeMessage(VALID_FROM_NAME);
    }

    @Test
    public void testInvalidFromMessage() {
        assertThatExceptionOfType(VerifyException.class)
                .isThrownBy(() -> this.instance.retrieve(INVALID_FROM_NAME))
                .withMessage(format(IS_REQUIRED_TEMPLATE, MESSAGE_FROM_VALUE));

        verifyNoInteractions(this.messageRepository);
    }
}
