package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;

import static com.codecool.webroute.HttpMethod.GET;
import static com.codecool.webroute.HttpMethod.POST;

class WebController {

    @WebRoute(method = GET, path = "/test")
    void onTest(HttpExchange httpExchange) throws IOException {
        sendDataToTwig(httpExchange, "This is a test!");
    }

    @WebRoute(method = GET, path = "/another-test")
    void onAnotherTest(HttpExchange httpExchange) throws IOException {
        sendDataToTwig(httpExchange, "This is another test!");
    }

    @WebRoute(method = POST, path = "/test")
    void onPostTest(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/another-test");
    }

    @WebRoute(method = POST, path = "/another-test")
    void onPostAnotherTest(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/test");
    }
    @WebRoute(method = GET, path = "/")
    void temp(HttpExchange httpExchange) throws IOException{
        sendDataToTwig(httpExchange,"Welcome");
    }
    @WebRoute(method = POST, path = "/")
    void temp2(HttpExchange httpExchange) throws IOException {
        redirectToPath(httpExchange, "/another-test");
    }

    private void sendDataToTwig(HttpExchange httpExchange, String dataString) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/test.twig");
        JtwigModel model = JtwigModel.newModel();
        Data data = new Data(dataString);
        model.with("data", data);
        ResponseSender.sendResponse(httpExchange, template.render(model));
    }

    private void redirectToPath(HttpExchange httpExchange, String path) throws IOException {
        httpExchange.getResponseHeaders().add("Location", path);
        httpExchange.sendResponseHeaders(301, -1);
    }
}
