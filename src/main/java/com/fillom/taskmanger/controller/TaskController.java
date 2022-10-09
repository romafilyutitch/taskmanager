package com.fillom.taskmanger.controller;

import com.fillom.taskmanger.model.Task;
import com.fillom.taskmanger.model.TaskPriority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class TaskController {

    private final List<Task> tasks = new ArrayList<Task>();

    public TaskController() {
        final Task buySomeFoodTask = new Task(0, "Buy some food", "I need to boy food for next week", LocalDateTime.now(), LocalDateTime.now().plus(3, ChronoUnit.HOURS), TaskPriority.LOW);
        final Task doHomeWorkTask = new Task(1, "Do homework", "I need to do my homework for tomorrow classes", LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.HOURS), TaskPriority.MEDIUM);
        final Task takePetsOutTask = new Task(2, "Take pets out", "I need to play with my pets outside", LocalDateTime.now(), LocalDateTime.now().plus(2, ChronoUnit.HOURS), TaskPriority.LOW);

        tasks.add(buySomeFoodTask);
        tasks.add(doHomeWorkTask);
        tasks.add(takePetsOutTask);
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    @GetMapping("/tasks/{id}")
    public Task findTaskById(@PathVariable Integer id) {
        return tasks.stream()
                .filter(tasks -> tasks.getId().equals(id))
                .findFirst()
                .get();
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        tasks.add(task);
        return task;
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) {
        task.setId(id);
        Task foundTask = tasks.stream()
                .filter(existingTask -> task.getId().equals(id))
                .findFirst()
                .get();
        int index = tasks.indexOf(foundTask);
        tasks.set(index, task);
        return task;
    }

    @DeleteMapping("/tasks/{id}")
    public boolean deleteTask(@PathVariable Integer id) {
        Task foundTask = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .get();
        return tasks.remove(foundTask);
    }
}
