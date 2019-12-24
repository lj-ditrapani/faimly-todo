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

        router.post("/additem").handler { routingContext ->
            todoList.addItem(routingContext.getBodyAsJson())
            routingContext.response().end("item added")
        }

        router.post("/done/:itemId").handler { routingContext ->
            todoList.completeItem(7)
            routingContext.response().end("doneitem")
        }

        router.post("/clean").handler { routingContext ->
            todoList.cleanList()
            routingContext.response().end("clean")
        }

        router.post("/prioritize").handler { routingContext ->
            todoList.prioritize(routingContext.getBodyAsJson())
            routingContext.response().end("clean")
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
