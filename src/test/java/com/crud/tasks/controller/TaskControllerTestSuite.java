package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
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

@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFetchEmptyTaskList() throws Exception {
        Mockito.when(service.getAllTasks()).thenReturn(List.of());
        Mockito.when(taskMapper.mapToTaskDtoList(List.of())).thenReturn(List.of());

        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldFetchTask() throws Exception {
        Task task = new Task(1L, "Test title", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");

        Mockito.when(service.getTask(1L)).thenReturn(task);
        Mockito.when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test title")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");
        Task task = new Task(1L, "Test title", "Test content");

        Mockito.when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "Updated title", "Updated content");
        Task task = new Task(1L, "Updated title", "Updated content");

        Mockito.when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        Mockito.when(service.saveTask(task)).thenReturn(task);

        mockMvc.perform(put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Edited test title")))
                .andExpect(jsonPath("$.content", is("Test Content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/v1/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
