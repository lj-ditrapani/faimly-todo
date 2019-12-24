package info.ditrapani.todo

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val PORT = 44770

class Verticle(val todoList: ITodoList, val logger: Logger) : CoroutineVerticle() {
    override suspend fun start() {
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        router.get("/list").handler { routingContext ->
            routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(todoList.list().encode())
        }

        router.post("/add").handler { routingContext ->
            todoList.addItem(routingContext.getBodyAsJson())
            routingContext.response().end("item added")
        }

        router.post("/complete").handler { routingContext ->
            val itemId = routingContext.request().getParam("itemId")
            todoList.completeItem(routingContext.getBodyAsJson())
            routingContext.response().end("item complete")
        }

        router.post("/clean").handler { routingContext ->
            todoList.cleanList()
            routingContext.response().end("list cleaned")
        }

        router.post("/prioritize").handler { routingContext ->
            todoList.prioritize(routingContext.getBodyAsJson())
            routingContext.response().end("prioritized")
        }

        val server = vertx.createHttpServer().requestHandler(router)
        logger.info("Starting server")
        server.listenAwait(PORT)
        logger.info("Server started on port $PORT")
    }
}

fun main(args: Array<String>) {
    val logger = LogManager.getLogger("todo")
    val todoList = TodoList()
    Vertx.vertx().deployVerticle(Verticle(todoList, logger))
}
