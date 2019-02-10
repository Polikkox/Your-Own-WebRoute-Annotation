package com.codecool.webroute;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

class WebServer {

    void start() throws IOException {

        WebController webController = new WebController();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new RootController(webController));
        server.setExecutor(null);
        server.start();
    }
}
