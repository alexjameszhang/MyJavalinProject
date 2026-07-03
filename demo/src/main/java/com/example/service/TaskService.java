package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import java.util.List;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(int id) {
        Task task = repository.findById(id);
        if (task == null) {
            throw new IllegalArgumentException("Task ID " + id + " does not exist.");
        }
        return task;
    }

    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        return repository.save(task);
    }

    public Task removeTask(int id) {
        if (repository.findById(id).getTitle() == null || repository.findById(id).getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        return repository.remove(repository.findById(id).getId());
    }

    public Task modifyTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        return repository.modify(task);
    }
}