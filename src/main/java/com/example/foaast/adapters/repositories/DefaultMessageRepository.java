package com.example.foaast.adapters.repositories;

import com.example.foaast.domain.repositories.MessageRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class DefaultMessageRepository implements MessageRepository {
    @Override
    public Mono<String> retrieveAwesomeMessage() {

        return WebClient.create()
                .get()
                .uri(URI.create("https://foaas.com/awesome/mike")) // TODO: read from env vars and use WebClient
                .retrieve()
                .bodyToMono(String.class);
    }
}
