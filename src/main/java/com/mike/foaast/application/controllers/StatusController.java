package com.mike.foaast.application.controllers;

import static com.mike.foaast.application.ApplicationConstants.STATUS_MESSAGE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.mike.foaast.rest.response.RestPingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/status")
public class StatusController {

    @RequestMapping(method = RequestMethod.GET, path="/ping", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<RestPingResponse>> ping() {
        RestPingResponse response = RestPingResponse.builder()
            .message(STATUS_MESSAGE)
            .build();

        return Mono.just(
            ResponseEntity
                .status(HttpStatus.OK)
                .body(response)
        );
    }
}
