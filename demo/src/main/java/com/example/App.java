package com.example;

import com.example.controller.TaskController;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import java.util.Objects;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        Javalin.create(config -> {
            // Register routes in the config.routes object
            config.routes.get("/api/tasks", controller::getAll);
            config.routes.get("/api/tasks/{id}", controller::getOne);
            config.routes.post("/api/tasks", controller::create);
            config.routes.put("/api/tasks/{id}", controller::modify);
            config.routes.delete("/api/tasks/{id}", controller::remove);

            // Examples for ticket #2614: demonstrate usage of new XML content types
            // 1) Use the ContentType enum constant directly (preferred, type-safe)
            config.routes.get("/xml/enum", ctx -> {
                ctx.contentType(ContentType.APPLICATION_XML);
                ctx.result("<data>Enum XML content</data>");
            });

            // 2) Extract the MIME string from the enum using getMimeType() method
            // This demonstrates how to get the raw string when needed
            config.routes.get("/xml/string", ctx -> {
                String mimeType = ContentType.APPLICATION_XML.getMimeType();
                if (!Objects.equals(ContentType.APPLICATION_XML_STRING, mimeType)) {
                    throw new IllegalStateException("MIME type mismatch: expected " + ContentType.APPLICATION_XML_STRING + " but was " + mimeType);
                }
                ctx.contentType(mimeType);
                ctx.result("<data>String XML content</data>");
            });

            // Exception handlers are also registered in config
            config.routes.exception(NumberFormatException.class, (e, ctx) ->
                ctx.status(400).json(Map.of("error", "ID parameter must be an integer."))
            );
        }).start(8080);
    }
}