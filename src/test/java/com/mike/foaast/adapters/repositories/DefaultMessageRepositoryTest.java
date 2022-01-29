package com.mike.foaast.adapters.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mike.foaast.TestConstants.MESSAGES_SERVICE_URL;

public class DefaultMessageRepositoryTest {

    private DefaultMessageRepository instance;

    @BeforeEach
    public void setUp() {
        this.instance = new DefaultMessageRepository(MESSAGES_SERVICE_URL);
    }

    @Test
    public void testRetrieveAwesomeMessage() {
        // Continue
    }
}
