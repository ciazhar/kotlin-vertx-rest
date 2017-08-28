package com.ciazhar.vertx.rest

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import com.ciazhar.vertx.rest.model.Whisky
import io.vertx.core.json.Json
import java.util.LinkedHashMap
import io.vertx.ext.web.RoutingContext


class MainVerticle : AbstractVerticle() {

    override fun start(fut: Future<Void>) {

        createSomeData()

        // Create a router object.
        val router = Router.router(vertx)

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler {
            routingContext -> routingContext
                .response()
                .putHeader("content-type", "text/html")
                .end("<h1>Hello from my first Vert.x 3 application</h1>")
        }
        router.get("/api/whiskies").handler ( this::getAll )

        // Serve static resources from the /assets directory
        router.route("/assets/*").handler(StaticHandler.create("assets"))

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

    // Store our product
    private val products = LinkedHashMap<Int, Whisky>()

    // Create some product
    private fun createSomeData() {
        val bowmore = Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay")
        products.put(bowmore.id, bowmore)
        val talisker = Whisky("Talisker 57° North", "Scotland, Island")
        products.put(talisker.id, talisker)
    }

    private fun getAll(routingContext: RoutingContext) {
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(products.values))
    }

}
