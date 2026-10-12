package com.example.menudb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = "text/html")
    public String home() {
        return """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>MenuDB API</title>
                </head>
                <body>
                    <h1>MenuDB API is running</h1>
                    <p>Send TheMenuDB API parameters to <code>/api/menudb</code>.</p>
                    <p>Example: <code>/api/menudb?yourDocumentedParameter=value</code></p>
                    <p>Replace the example parameter with one from TheMenuDB API documentation.</p>
                </body>
                </html>
                """;
    }
}
