package com.mike.foaast.domain.usecases.messages.impl;

import com.mike.foaast.domain.repositories.MessageRepository;
import com.mike.foaast.domain.usecases.messages.RetrieveAwesomeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultRetrieveAwesomeMessage implements RetrieveAwesomeMessage {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultRetrieveAwesomeMessage.class);

    private final MessageRepository messageRepository;

    public DefaultRetrieveAwesomeMessage(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Mono<String> retrieve(String from) {
        LOGGER.info("Retrieving an 'Awesome' message from {}", from);
        return messageRepository.retrieveAwesomeMessage(from)
                .doOnSuccess(message -> LOGGER.info("Successfully retrieved an 'Awesome' message from {}", from))
                .doOnError(e -> LOGGER.error("An error occurred while retrieving an 'Awesome' message from {}", from));
    }
}
