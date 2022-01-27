package com.mike.foaast.domain.usecases.messages;

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
