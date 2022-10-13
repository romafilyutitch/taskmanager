package com.fillom.taskmanger.service;

import com.fillom.taskmanger.TaskRepository;
import com.fillom.taskmanger.model.Task;
import com.fillom.taskmanger.model.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceUnitTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    private static final LocalDateTime CREATED_AT_LOCAL_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime ENDS_AT_LOCAL_DATE_TIME = CREATED_AT_LOCAL_DATE_TIME.plus(1, ChronoUnit.HOURS);

    private Task firstTask;
    private Task secondTask;
    private Task thirdTask;

    @BeforeEach
    public void setUp() {
        firstTask = new Task(1, "Read book", "I need to read book", CREATED_AT_LOCAL_DATE_TIME, ENDS_AT_LOCAL_DATE_TIME, TaskPriority.LOW);
        secondTask = new Task(2, "Take pills", "I need to take pills", CREATED_AT_LOCAL_DATE_TIME, ENDS_AT_LOCAL_DATE_TIME, TaskPriority.HIGH);
        thirdTask = new Task(3, "Take the dog out", "I need to take the dog out", CREATED_AT_LOCAL_DATE_TIME, ENDS_AT_LOCAL_DATE_TIME, TaskPriority.MEDIUM);

        List<Task> tasks = Arrays.asList(firstTask, secondTask, thirdTask);

        Mockito.when(taskRepository.findAll()).thenReturn(tasks);
    }

    @Test
    public void findAll_shouldReturnThreeTasks() {
        List<Task> foundTasks = taskService.findAll();

        assertNotNull(foundTasks, "found tasks list should not be null");
        assertEquals(3, foundTasks.size(), "Found tasks list size should 3");
    }

    @Test
    public void findAll_whenThereIsNoTasks_shouldReturnEmptyList() {
        Mockito.when(taskRepository.findAll()).thenReturn(List.of());

        List<Task> foundTasks = taskService.findAll();

        assertNotNull(foundTasks);
        assertTrue(foundTasks.isEmpty());
    }

    @Test
    public void findAll_shouldReturnTaskObjects() {
        List<Task> foundTasks = taskService.findAll();
        Task foundTask = foundTasks.get(0);

        assertEquals(firstTask, foundTask);
    }

    @Test
    public void whenValidId_thenTaskShouldBeFound() {
        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(firstTask));

        Task foundTask = taskService.findById(1);

        assertEquals(firstTask, foundTask);
    }

    @Test
    public void shouldThrowException_whenThereIsNoTaskWithId() {
        Mockito.when(taskRepository.findById(5)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findById(5));
    }

    @Test
    public void shouldSaveTask() {
        Task taskToSave = new Task(4, "Buy some food", "I need to buy some food", CREATED_AT_LOCAL_DATE_TIME, ENDS_AT_LOCAL_DATE_TIME, TaskPriority.MEDIUM);

        Mockito.when(taskRepository.save(taskToSave)).thenReturn(taskToSave);

        Task savedTask = taskService.create(taskToSave);

        assertEquals(taskToSave, savedTask);
    }

    @Test
    public void shouldUpdateTask() {
        Task taskToUpdate = new Task(3, "Buy some food", "I need to buy some food", CREATED_AT_LOCAL_DATE_TIME, ENDS_AT_LOCAL_DATE_TIME, TaskPriority.MEDIUM);

        Mockito.when(taskRepository.findById(3)).thenReturn(Optional.of(thirdTask));
        Mockito.when(taskRepository.save(taskToUpdate)).thenReturn(taskToUpdate);

        Task updatedTask = taskService.update(3, taskToUpdate);

        assertEquals(taskToUpdate, updatedTask);
    }

    @Test
    public void shouldDeleteTask() {
        Mockito.doNothing().when(taskRepository).deleteById(1);

        taskService.delete(1);

        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1);

    }

}
