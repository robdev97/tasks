package com.crud.tasks.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TrelloConfig {

    @Value("https://api.trello.com/1")
    private String trelloApiEndpoint;
    @Value("cb43343c3b8e2597719660605b7019aa")
    private String trelloAppKey;
    @Value("ATTAd7a2827f6c4b7b74cc8c9f543abdc050c886ba5f1828e152721acd59c18dd006FA27C099")
    private String trelloToken;
    @Value("robertlit97")
    private String trelloUser;
}
