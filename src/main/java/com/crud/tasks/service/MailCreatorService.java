package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview", adminConfig.getPreviewMessage());
        context.setVariable("goodbye", adminConfig.getGoodbyeMessage());
        context.setVariable("company", adminConfig.getCompanyDetails());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        return templateEngine.process("mail/created-trello-card-email", context);
    }

    public String buildTasksCountEmail(String message) {
        Context context = new Context();
        context.setVariable("preview", "Daily tasks summary");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Check your tasks");
        context.setVariable("goodbye", "Have a productive day!");
        context.setVariable("company", "Kodilla App © 2025");
        context.setVariable("show_button", true);


        List<String> functionalities = List.of(
                "You can manage your tasks",
                "Provides connection with Trello Account",
                "Application allows sending tasks to Trello"
        );
        context.setVariable("application_functionality", functionalities);

        return templateEngine.process("mail/daily-tasks-mail", context);
    }
}
