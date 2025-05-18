package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTestSuite {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void testMapToTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");

        // When
        Task task = taskMapper.mapToTask(taskDto);

        // Then
        assertEquals(1L, task.getId());
        assertEquals("Test title", task.getTitle());
        assertEquals("Test content", task.getContent());
    }

    @Test
    void testMapToTaskDto() {
        // Given
        Task task = new Task(2L, "Another title", "Another content");

        // When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // Then
        assertEquals(2L, taskDto.getId());
        assertEquals("Another title", taskDto.getTitle());
        assertEquals("Another content", taskDto.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        // Given
        List<Task> tasks = List.of(
                new Task(1L, "Task1", "Content1"),
                new Task(2L, "Task2", "Content2")
        );

        // When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(tasks);

        // Then
        assertEquals(2, taskDtoList.size());

        TaskDto dto1 = taskDtoList.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Task1", dto1.getTitle());
        assertEquals("Content1", dto1.getContent());

        TaskDto dto2 = taskDtoList.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("Task2", dto2.getTitle());
        assertEquals("Content2", dto2.getContent());
    }
}