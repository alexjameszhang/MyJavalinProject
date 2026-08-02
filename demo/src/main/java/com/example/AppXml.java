package com.example;

import io.javalin.Javalin;
import io.javalin.http.ContentType;

public class AppXml {
    public static void main(String[] args) {
        Javalin.create(config -> {
            //#2614: 1)Use the new ContentType enum constant (preferred and type-safe)
            config.routes.get("/xml/enum/application", ctx -> {
                ctx.contentType(ContentType.APPLICATION_XML);
                ctx.result("<data>Enum constant ContentType.APPLICATION_XML</data>");
            });

            //#2614: 2)Use the new ContentType String constant when needed.
            //         The enum getMimeType() method converts the new enum constant to String
            config.routes.get("/xml/string", ctx -> {
                String mimeType = ContentType.APPLICATION_XML.getMimeType();
                // Java assert (disabled by default). Enable with -ea when running the JVM
                assert ContentType.APPLICATION_XML_STRING.equals(mimeType) : "MIME type mismatch: expected " + ContentType.APPLICATION_XML_STRING + " but was " + mimeType;
                ctx.contentType(ContentType.APPLICATION_XML_STRING);
                ctx.result("<data>String constant ContentType.APPLICATION_XML_STRING</data>");
            });

            //#2614: backward compatible for existing enum constant ContentType.TEXT_XML
            config.routes.get("/xml/enum/text", ctx -> {
                ctx.contentType(ContentType.TEXT_XML);
                ctx.result("<data>exising Enum constant ContentType.TEXT_XML</data>");
            });
        }).start(8080);
    }
}