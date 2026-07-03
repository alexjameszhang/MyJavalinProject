package com.example.repository;

import com.example.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskRepository {
    private final Map<Integer, Task> database = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public TaskRepository() {
        save(new Task(0, "Learn Javalin explicit routing", new int[]{5, 26, 2003}));
    }

    public List<Task> findAll() {
        return new ArrayList<>(database.values());
    }

    public Task findById(int id) {
        return database.get(id);
    }

    public Task save(Task task) {
        if (task.getId() == 0) { //edge case where there already is a task with ID 1; only when initialized and not saved using save method
            task.setId(idGenerator.getAndIncrement());
        }
        database.put(task.getId(), task);
        return task;
    }

    public Task remove(int id) {
        Task temp = database.get(id);
        database.remove(id);
        return temp;
    }

    public Task modify(Task task) {
        Task temp = database.remove(task.getId());
        database.put(task.getId(), task);
        return temp; 
    }
}