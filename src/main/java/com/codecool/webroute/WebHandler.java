package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebHandler implements com.sun.net.httpserver.HttpHandler {

    private WebController webController;

    WebHandler(WebController webController) {
        this.webController = webController;
    }

    public void handle(HttpExchange httpExchange) {
        String path = httpExchange.getRequestURI().getPath();
        HttpMethod httpMethod = selectHTTPMethod(httpExchange);

        Class<WebController> webControllerClassAnnot = WebController.class;
        for (Method method : webControllerClassAnnot.getDeclaredMethods()) {
            if (method.isAnnotationPresent(WebRoute.class)) {
                WebRoute webRoute = method.getAnnotation(WebRoute.class);

                if (webRoute.method().equals(httpMethod) && webRoute.path().equals(path)) {
                    try {
                        method.invoke(webController, httpExchange);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private HttpMethod selectHTTPMethod(HttpExchange httpExchange){

        if(httpExchange.getRequestMethod().equals("GET")){
            return HttpMethod.GET;
        }
        else {
            return HttpMethod.POST;
        }
    }
}
