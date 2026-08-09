package com.example;

import io.javalin.Javalin;
import io.javalin.http.ContentType;

// maven build javalin\javaline module only -> D:\ws_idea\javalin>mvn clean install -pl javalin
// maven test ContentTypeTest.kt only       -> D:\ws_idea\javalin>mvn test -pl javalin -Dtest=ContentTypeTest
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
                assert ContentType.APPLICATION_XML_MIME_TYPE.equals(mimeType) : "MIME type mismatch: expected " + ContentType.APPLICATION_XML_MIME_TYPE + " but was " + mimeType;
                ctx.contentType(ContentType.APPLICATION_XML_MIME_TYPE);
                ctx.result("<data>String constant ContentType.APPLICATION_XML_MIME_TYPE</data>");
            });

            //#2614: backward compatible for existing enum constant ContentType.TEXT_XML
            config.routes.get("/xml/enum/text", ctx -> {
                ctx.contentType(ContentType.TEXT_XML);
                ctx.result("<data>exising Enum constant ContentType.TEXT_XML</data>");
            });

            //#2614: new code that added ContentType.APPLICATION_XML breaks backward compatibility
            config.routes.get("/xml/enum/error", ctx -> {
                ContentType type = ContentType.contentType("application/xml");
                boolean isBackwardCompatible = ContentType.APPLICATION_POM == type;
                assert ContentType.APPLICATION_POM == type : "backward compatibility issue - ContentType.APPLICATION_POM expected, but got " + type;
                //The backward compatibility issue can be fixed if placing ContentType.APPLICATION_XML after ContentType.APPLICATION_POM
                ctx.contentType(ContentType.APPLICATION_XML);
                ctx.result("<data>backward compatible for ContentType.APPLICATION_POM ? " + isBackwardCompatible + "</data>");
            });

        }).start(8080);
    }
}