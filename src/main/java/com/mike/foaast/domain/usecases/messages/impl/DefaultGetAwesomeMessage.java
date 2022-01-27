package com.mike.foaast.domain.usecases.messages.impl;

import com.mike.foaast.domain.repositories.MessageRepository;
import com.mike.foaast.domain.usecases.messages.GetAwesomeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultGetAwesomeMessage implements GetAwesomeMessage {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultGetAwesomeMessage.class);

    private final MessageRepository messageRepository;

    public DefaultGetAwesomeMessage(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Mono<String> get(String from) {
        return messageRepository.retrieveAwesomeMessage(from);
    }
}
