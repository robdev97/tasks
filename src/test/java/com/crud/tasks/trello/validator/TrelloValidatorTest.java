package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrelloValidatorTest {

    private TrelloValidator trelloValidator;

    @BeforeEach
    void setUp() {
        trelloValidator = new TrelloValidator();
    }
    @Test
    void ShouldValidateCardWithTestInName() {
        //Given
        TrelloCard card = new TrelloCard("test card", "desc", "top", "1");
        //When
        trelloValidator.validateCard(card);
        //Then
        //Nie ma wyjątku, logika działa :)
    }
    @Test
    void ShouldValidateCardWithoutTestInName() {
        //Given
        TrelloCard card = new TrelloCard("Normal card", "desc", "top", "1");
        //When
        trelloValidator.validateCard(card);
        //Then
        //To samo co wyżej - brak błędów
    }
    @Test
    void shouldFilterOutBoardsWithNameTest() {
        //Given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "test", List.of()),
                new TrelloBoard("2", "Production board", List.of())
        );
        //When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(boards);
        //Then
        assertEquals(1, result.size());
        assertEquals("Production board", result.get(0).getName());
    }
    @Test
    void shouldReturnAllBoardsWhenNoneHaveNameTest() {
        //Given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "Project A", List.of()),
                new TrelloBoard("2", "Project B", List.of())
        );
        //When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(boards);
        //Then
        assertEquals(2, result.size());
    }
    @Test
    void shouldReturnEmptyListWhenAllBoardsHaveNameTest() {
        //Given
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "test", List.of()),
                new TrelloBoard("2", "TEST", List.of())
        );
        //When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(boards);
        //Then
        assertTrue(result.isEmpty());
    }

}
