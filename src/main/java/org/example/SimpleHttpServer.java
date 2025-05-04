import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        int port = 8081; // The port the server will listen on

        // 1. HttpServer instance
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // 2. Endpoints and assign handlers
        server.createContext("/", new RootHandler());
        server.createContext("/hello", new HelloHandler());
        server.createContext("/data", new DataHandler());

        // 3. Executor for handling requests
        server.setExecutor(Executors.newCachedThreadPool()); // Use a cached thread pool

        // 4. Start Server
        server.start();

        System.out.println();
        System.out.println("Server started on port " + port);
        System.out.println("-------------------------------------------");
        System.out.println("Access: http://localhost:" + port + "/");
        System.out.println("Access: http://localhost:" + port + "/hello");
        System.out.println("Access: http://localhost:" + port + "/data");
//        System.out.println("Press Ctrl+C to stop.");
    }

    // --- Handler Implementations ---

    // Handler for the root path "/"
    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "<h1>Welcome to the Root!</h1>Try <a href=\"/hello\">/hello</a> or <a href=\"/data\">/data</a>";
                sendResponse(exchange, 200, response, "text/html");
            } else {
                sendMethodNotAllowed(exchange);
            }
        }
    }

    // Handler for the "/hello" path
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Hello from the /hello endpoint!";
                sendResponse(exchange, 200, response, "text/plain");
            } else {
                sendMethodNotAllowed(exchange);
            }
        }
    }

    // Handler for the "/data" path
    static class DataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                // Example: Return simple JSON data
                String response = "{\"message\": \"Here is some data\", \"value\": 123}";
                sendResponse(exchange, 200, response, "application/json");
            } else {
                sendMethodNotAllowed(exchange);
            }
        }
    }

    // Utility Methods

    // Sends a standard text/plain or text/html response.

    private static void sendResponse(HttpExchange exchange, int statusCode, String responseBody, String contentType) throws IOException {
        byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=" + StandardCharsets.UTF_8.name());
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }


    // Sends a 405 Method Not Allowed response.

    private static void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        String response = "405 Method Not Allowed";
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Allow", "GET");
        exchange.sendResponseHeaders(405, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}