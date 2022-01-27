package com.mike.foaast.domain.usecases.messages;

import reactor.core.publisher.Mono;

/**
 * Get awesome message.
 */
@FunctionalInterface
public interface RetrieveAwesomeMessage {
    /**
     * It fetches an awesome message and
     *
     * @return
     */
    Mono<String> retrieve(String from);
}
