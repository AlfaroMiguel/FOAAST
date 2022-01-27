package com.mike.foaast.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ping response.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPingResponse {
    /**
     * Ping response message.
     */
    private String message;
}
