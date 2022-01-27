package com.mike.foaast.domain.repositories;

import reactor.core.publisher.Mono;

/**
 * Message repository.
 */
public interface MessageRepository {

   /**
    * Retrieves an Awesome message from the Message service.
    *
    * @param from Who requests the message.
    * @return A Mono of the string containing the message.
    */
   Mono<String> retrieveAwesomeMessage(String from);
}
