package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RootController implements HttpHandler {

    private WebController webController;

    RootController(WebController webController) {
        this.webController = webController;
    }

    public void handle(HttpExchange httpExchange) {

        String path = httpExchange.getRequestURI().getPath();
        String URL = parseURL(path);
        HttpMethod httpMethod = selectHTTPMethod(httpExchange);

        Class<WebController> aClass = WebController.class;

        for (Method method : aClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(WebRoute.class)) {

                Annotation annotation = method.getAnnotation(WebRoute.class);
                WebRoute webRoute = (WebRoute) annotation;

                if (webRoute.method().equals(httpMethod) && webRoute.path().equals(URL)) {

                    try {
                        method.invoke(webController, httpExchange);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String parseURL(String path) {

        String[] parsePath = path.split("/");
        if(parsePath.length == 0){
            return "/";
        }
        else {
            return "/" + parsePath[1];
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
