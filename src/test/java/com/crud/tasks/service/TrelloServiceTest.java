package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TrelloServiceTest {

    private TrelloClient trelloClient;
    private SimpleEmailService emailService;
    private AdminConfig adminConfig;
    private TrelloService trelloService;

    @BeforeEach
    void beforeEach() {
        trelloClient = mock(TrelloClient.class);
        emailService = mock(SimpleEmailService.class);
        adminConfig = mock(AdminConfig.class);
        trelloService = new TrelloService(trelloClient, emailService, adminConfig);
    }

    @Test
    void shouldFetchTrelloBoards() {
        //Given
        TrelloBoardDto boardDto = new TrelloBoardDto("1", "Test Board", List.of());

        when(trelloClient.getTrelloBoards()).thenReturn(List.of(boardDto));
        //When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Test Board", result.get(0).getName());
    }

    @Test
    void shouldCreateTrelloCardAndSendEmail() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("Card 1", "desc", "top", "list_id");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1", "Card 1", "http://shortUrl.com");

        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@email.com");
        //When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);
        //Then
        assertEquals("1", result.getId());
        assertEquals("Card 1", result.getName());
        verify(emailService, times(1)).send(any(Mail.class));

    }

    @Test
    void shouldNotSendEmailWhenCardIsNull() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("Card 1", "desc", "top", "list_id");

        when(trelloClient.createNewCard(cardDto)).thenReturn(null);
        //When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);
        //Then
        assertNull(result);
        verify(emailService, never()).send(any(Mail.class));
    }

    @Test
    void shouldSendCorrectMailContent() {
        //GIven
        TrelloCardDto cardDto = new TrelloCardDto("New Feature", "desc", "bottom", "listId");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("10", "New Feature", "http://shortUrl.com");

        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@email.com");

        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        //When
        trelloService.createTrelloCard(cardDto);
        //Then
        verify(emailService).send(mailCaptor.capture());
        Mail sentMail = mailCaptor.getValue();
        assertEquals("admin@email.com", sentMail.getMailTo());
        assertEquals(TrelloService.SUBJECT, sentMail.getSubject());
        assertEquals(sentMail.getMessage().contains("New card: New Feature"), true);
    }
}
