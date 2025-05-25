package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${admin.mail}")
    private String adminMail;

    @Value("${admin.name}")
    private String adminName;

    @Value("${info.preview.message}")
    private String previewMessage;

    @Value("${info.goodbye.message}")
    private String goodbyeMessage;

    @Value("${info.company.details}")
    private String companyDetails;
}
