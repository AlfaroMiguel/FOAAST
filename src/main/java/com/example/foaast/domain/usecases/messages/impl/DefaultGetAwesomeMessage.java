package com.example.foaast.domain.usecases.messages.impl;

import com.example.foaast.domain.usecases.messages.GetAwesomeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultGetAwesomeMessage implements GetAwesomeMessage {

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultGetAwesomeMessage.class);

    @Override
    public Mono<Void> get() {
        LOGGER.info("Called DefaultGetAwesomeMessage:get");
        return Mono.empty();
    }
}
