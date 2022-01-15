package com.example.foaast.domain.repositories;

import reactor.core.publisher.Mono;

/**
 *
 */
public interface MessageRepository {

   /**
    *
    * @return
    */
   Mono<String> retrieveAwesomeMessage(String from);
}
