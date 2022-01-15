package com.example.foaast.domain.usecases.messages;

import reactor.core.publisher.Mono;

/**
 *
 */
@FunctionalInterface
public interface GetAwesomeMessage {
    /**
     *
     * @return
     */
    Mono<String> get(String from);
}
