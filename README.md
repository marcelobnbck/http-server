# http-server

## Simple Java HTTP Server
A lightweight HTTP server implementation using Java's built-in com.sun.net.httpserver package. This server provides a simple way to expose REST-like endpoints with minimal configuration.

### Features
- Built with Java's native HTTP server capabilities (no external dependencies)
- Multi-threaded request handling with a cached thread pool
- Three pre-configured endpoints demonstrating different response types:
  - HTML responses
  - Plain text responses
  - JSON responses
- Clean, easy-to-understand code structure with handler separation

### Requirements
- Java 8 or higher
- No external dependencies required

### Installation
1. Clone this repository:
```
git clone https://github.com/yourusername/simple-java-http-server.git
```

2. Navigate to the project directory:
```
cd SimpleHttpServer
```

3. Compile the Java file:
```
javac SimpleHttpServer.java
```

### Usage
- Run the server with:
```
java SimpleHttpServer
```

Once started, the server will listen on port 8081 and display available endpoints in the console.

### Available Endpoints
- http://localhost:8081/ - Returns a welcome HTML page with links to other endpoints
- http://localhost:8081/hello - Returns a plain text greeting
- http://localhost:8081/data - Returns a JSON response with sample data