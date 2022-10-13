package com.fillom.taskmanger.repository;

import com.fillom.taskmanger.TaskRepository;
import com.fillom.taskmanger.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TaskRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void whenFindById_thenReturnTask() {
        Optional<Task> optionalTask = taskRepository.findById(1);

        assertTrue(optionalTask.isPresent());
        assertEquals(1, optionalTask.get().getId());
    }
}
