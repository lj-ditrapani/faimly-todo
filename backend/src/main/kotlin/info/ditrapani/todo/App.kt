package info.ditrapani.todo

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Verticle(val logger: Logger) : CoroutineVerticle() {
    val port = 44770

    override suspend fun start() {
        val router = Router.router(vertx)
        router.get("/list").handler { routingContext ->
            routingContext.response().end("list")
        }
        router.post("/additem").handler { routingContext ->
            routingContext.response().end("additem")
        }
        router.post("/done/:itemId").handler { routingContext ->
            routingContext.response().end("doneitem")
        }
        router.post("/clean").handler { routingContext ->
            routingContext.response().end("clean")
        }
        val server = vertx.createHttpServer()
        server.requestHandler(router)
        logger.info("Starting server")
        server.listenAwait(port)
        logger.info("Server started on port $port")
    }
}

fun main(args: Array<String>) {
    val logger = LogManager.getLogger("todo")
    Vertx.vertx().deployVerticle(Verticle(logger))
}
