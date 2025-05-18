package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrelloMapperTestSuite {
    private final TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    void mapToBoards() {
        //Given
        TrelloListDto listDto = new TrelloListDto("1", "List", false);
        TrelloBoardDto boardDto = new TrelloBoardDto("1", "Board", List.of(listDto));
        //When
        List<TrelloBoard> result = trelloMapper.mapToBoards(List.of(boardDto));
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Board", result.get(0).getName());
        assertEquals(1, result.get(0).getLists().size());
    }
    @Test
    void mapToBoardsDto() {
        //Given
        TrelloList listDto = new TrelloList("1", "List", false);
        TrelloBoard boardDto = new TrelloBoard("1", "Board", List.of(listDto));
        //When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(List.of(boardDto));
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Board", result.get(0).getName());
        assertEquals(1, result.get(0).getLists().size());
    }
    @Test
    void testMapToList() {
        //Given
        TrelloListDto listDto = new TrelloListDto("1", "List", true);
        //When
        List<TrelloList> result = trelloMapper.mapToList(List.of(listDto));
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("List", result.get(0).getName());
        assertEquals(result.get(0).isClosed(), true);
    }
    @Test
    void testMapToListDto() {
        //Given
        TrelloList list = new TrelloList("1", "List", true);
        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(List.of(list));
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("List", result.get(0).getName());
        assertEquals(result.get(0).isClosed(), true);

    }
    @Test
    void testMapToCard() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Card", "Desc", "bottom", "2");
        // When
        TrelloCard result = trelloMapper.mapToCard(cardDto);
        // Then
        assertEquals("Card", result.getName());
        assertEquals("Desc", result.getDescription());
        assertEquals("bottom", result.getPos());
        assertEquals("2", result.getListId());
    }
    @Test
    void testMapToCardDto() {
        // Given
        TrelloCard card = new TrelloCard("Card", "Desc", "top", "1");
        // When
        TrelloCardDto result = trelloMapper.mapToCardDto(card);
        // Then
        assertEquals("Card", result.getName());
        assertEquals("Desc", result.getDescription());
        assertEquals("top", result.getPos());
        assertEquals("1", result.getListId());
    }
}
