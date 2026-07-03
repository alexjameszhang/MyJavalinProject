package com.example.controller;

import com.example.model.Task;
import com.example.service.TaskService;
import io.javalin.http.Context;
import java.util.Map;

public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    public void getAll(Context ctx) {
        ctx.json(service.getAllTasks());
    }

    public void getOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            ctx.json(service.getTaskById(id));
        } catch (IllegalArgumentException e) {
            ctx.status(404).json(Map.of("error", e.getMessage()));
        }
    }

    public void create(Context ctx) {
        try {
            Task task = ctx.bodyAsClass(Task.class);
            ctx.status(201).json(service.createTask(task));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    public void remove(Context ctx) {
        try {
            String rawId = ctx.pathParam("id");
            int id = Integer.parseInt(rawId);
            ctx.status(201).json(service.removeTask(id));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    public void modify(Context ctx) {
        try {
            Task task = ctx.bodyAsClass(Task.class);
            ctx.status(201).json(service.modifyTask(task));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }
}