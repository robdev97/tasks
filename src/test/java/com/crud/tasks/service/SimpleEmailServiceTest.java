package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void shouldSendEmail() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    void shouldSendEmailWithCc() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .toCc("Test to CC")
                .build();

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        //When
        simpleEmailService.send(mail);
        //Then
        verify(javaMailSender, times(1)).send(captor.capture());
        SimpleMailMessage capturedMessage = captor.getValue();

        assertArrayEquals(new String[]{"test@test.com"}, capturedMessage.getTo());
        assertEquals("Test", capturedMessage.getSubject());
        assertEquals("Test message", capturedMessage.getText());
        assertArrayEquals(new String[]{"Test to CC"}, capturedMessage.getCc());

    }

    @Test
    void shouldSendEmailWithoutCc() {
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        //When
        simpleEmailService.send(mail);
        //Then
        verify(javaMailSender, times(1)).send(captor.capture());
        SimpleMailMessage capturedMessage = captor.getValue();

        assertArrayEquals(new String[]{"test@test.com"}, capturedMessage.getTo());
        assertEquals("Test", capturedMessage.getSubject());
        assertEquals("Test message", capturedMessage.getText());
        assertNull(capturedMessage.getCc());

    }
}