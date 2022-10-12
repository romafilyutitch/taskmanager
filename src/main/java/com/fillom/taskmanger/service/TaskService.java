package com.fillom.taskmanger.service;


import com.fillom.taskmanger.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();
    Task findById(Integer id);
    Task create(Task task);
    Task update(Integer id, Task task);
    void delete(Integer id);
}
