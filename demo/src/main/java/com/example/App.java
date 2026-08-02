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
            // Register routes in the config.routes object
            config.routes.get("/api/tasks", controller::getAll);
            config.routes.get("/api/tasks/{id}", controller::getOne);
            config.routes.post("/api/tasks", controller::create);
            config.routes.put("/api/tasks/{id}", controller::modify);
            config.routes.delete("/api/tasks/{id}", controller::remove);

            // Exception handlers are also registered in config
            config.routes.exception(NumberFormatException.class, (e, ctx) -> {
                ctx.status(400).json(Map.of("error", "ID parameter must be an integer."));
            });
        }).start(8080);
    }
}