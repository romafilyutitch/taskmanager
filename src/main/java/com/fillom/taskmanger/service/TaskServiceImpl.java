package com.fillom.taskmanger.service;

import com.fillom.taskmanger.TaskRepository;
import com.fillom.taskmanger.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task update(Integer id, Task task) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Task foundTask = optionalTask.orElseThrow(TaskNotFoundException::new);
        task.setId(foundTask.getId());
        taskRepository.save(task);
        return task;
    }

    @Override
    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }
}
