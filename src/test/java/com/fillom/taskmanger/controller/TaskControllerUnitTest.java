package com.fillom.taskmanger.controller;

import com.fillom.taskmanger.model.Task;
import com.fillom.taskmanger.model.TaskPriority;
import com.fillom.taskmanger.service.TaskService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void whenGetTasks_thenReturnJsonArray() throws Exception {
        Task task = new Task(1, "Read book", "I should read a book", LocalDateTime.now(), LocalDateTime.now().plus(3, ChronoUnit.HOURS), TaskPriority.LOW);
        List<Task> tasks = Arrays.asList(task);

        Mockito.when(taskService.findAll()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is(task.getTitle())));
    }
}
