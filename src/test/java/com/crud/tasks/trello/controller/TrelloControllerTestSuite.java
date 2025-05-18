package com.crud.tasks.trello.controller;

import com.crud.tasks.controller.TrelloController;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.mapper.TrelloFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrelloController.class)
public class TrelloControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        // Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "Test List", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "Test Board", trelloLists));

        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        // When & Then
        mockMvc.perform(get("/v1/trello/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Test Board")))
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("Test List")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Card", "Description", "top", "123");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("321", "Card", "http://short.url/card");

        Mockito.when(trelloFacade.createCard(Mockito.any(TrelloCardDto.class))).thenReturn(createdCard);

        // When & Then
        mockMvc.perform(post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("321")))
                .andExpect(jsonPath("$.name", is("Card")))
                .andExpect(jsonPath("$.shortUrl", is("http://short.url/card")));
    }
}
