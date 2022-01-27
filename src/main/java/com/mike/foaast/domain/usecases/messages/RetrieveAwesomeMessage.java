package com.mike.foaast.domain.usecases.messages;

import reactor.core.publisher.Mono;

/**
 * Retrieves an awesome message.
 */
@FunctionalInterface
public interface RetrieveAwesomeMessage {

    /**
     * Retrieves an awesome message.
     *
     * @param from Who requests the message.
     * @return A Mono of the string containing the message in HTML format.
     */
    Mono<String> retrieve(String from);
}
