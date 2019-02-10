# WebRoute-custom-annotation

Mini-webserver that uses custom annotations to route browser requests to specific handler methods

## Description

Your task is to create a mini-webserver that uses annotations to route browser requests to specific handler methods, 
basically a simpler version of routing mechanism.

Use Java 8's built-in webserver.

Your task is to create a custom annotation called `@WebRoute` which can be used to annotate methods. 
Methods annotated with `@WebRoute("path")` will be your HTTP request handlers. They are called whenever a request
accepted by the webserver matches the "path" value in a @WebRoute annotation. Use reflection to find the right 
method for an incoming request.

Here is an example:

```
@WebRoute("/test")
void onTest(HttpExchange requestData) {
    // Here goes the code to handle all requests going to myserver.com/test
    // and to return something
}
```

Support GET, POST, etc. as well. You will need to name the parameters in your annotation, for example:

```
@WebRoute(method=POST, path = "/users")
void onTest(HttpExchange requestData) {
    // here goes the code to handle POST requests going to myserver.com/users
}
```

## More info

Project made for [Codecool](https://codecool.com/) programming course.
