package com.example;

import com.example.controller.TaskController;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import io.javalin.Javalin;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        Javalin app = Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                // Inline API builder is another way to structure routes
            });
        });

        // Register routes explicitly 
        app.get("/api/tasks", controller::getAll);
        app.get("/api/tasks/{id}", controller::getOne);
        app.post("/api/tasks", controller::create);
        app.put("/api/tasks/{id}", controller::modify);
        app.delete("/api/tasks/{id}", controller::remove);

        app.exception(NumberFormatException.class, (e, ctx) -> {
            ctx.status(400).json(Map.of("error", "ID parameter must be an integer."));
        });

        app.start(8080);
    }
}