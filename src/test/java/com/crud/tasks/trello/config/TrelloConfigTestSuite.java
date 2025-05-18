package com.crud.tasks.trello.config;

import com.crud.tasks.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloConfigTestSuite {

    @Autowired
    private TrelloConfig trelloConfig;

    @Test
    void testTrelloConfigFields() {
        // Then
        assertNotNull(trelloConfig.getTrelloApiEndpoint());
        assertTrue(trelloConfig.getTrelloApiEndpoint().contains("https://api.trello.com"));

        assertNotNull(trelloConfig.getTrelloAppKey());
        assertFalse(trelloConfig.getTrelloAppKey().isBlank());

        assertNotNull(trelloConfig.getTrelloToken());
        assertFalse(trelloConfig.getTrelloToken().isBlank());

        assertEquals("robertlit97", trelloConfig.getTrelloUser());
    }
}

