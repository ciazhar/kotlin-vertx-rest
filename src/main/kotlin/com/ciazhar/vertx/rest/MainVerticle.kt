package com.ciazhar.vertx.rest


import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler


class MainVerticle : AbstractVerticle() {

    override fun start(fut: Future<Void>) {
        // Create a router object.
        val router = Router.router(vertx)

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler {
            routingContext -> routingContext
                .response()
                .putHeader("content-type", "text/html")
                .end("<h1>Hello from my first Vert.x 3 application</h1>")
        }

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx
            .createHttpServer()
            .requestHandler { router.accept(it) }
            .listen(
                // Retrieve the port from the configuration,
                // default to 8080.
                    config().getInteger("http.port", 8080)!!
            ) { result ->
                if (result.succeeded()) {
                    fut.complete()
                } else {
                    fut.fail(result.cause())
                }
            }
    }
}
