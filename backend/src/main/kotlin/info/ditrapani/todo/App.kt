package info.ditrapani.todo

import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

const val PORT = 44770

class Verticle(
    private val todoList: ITodoList,
    private val logger: Logger
) : CoroutineVerticle() {
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
            val result = todoList.completeItem(routingContext.getBodyAsJson())
            onResult(result, "item complete", routingContext.response())
        }

        router.post("/clean").handler { routingContext ->
            todoList.cleanList()
            routingContext.response().end("list cleaned")
        }

        router.post("/prioritize").handler { routingContext ->
            val result = todoList.prioritize(routingContext.getBodyAsJson())
            onResult(result, "prioritized", routingContext.response())
        }

        val server = vertx.createHttpServer().requestHandler(router)
        logger.info("Starting server")
        server.listenAwait(PORT)
        logger.info("Server started on port $PORT")
    }
}

fun onResult(result: Result, successMessage: String, response: HttpServerResponse) =
    when (result) {
        is Success -> response.end(successMessage)
        is Fail -> response.setStatusCode(400).end(result.reason)
    }

fun main(args: Array<String>) {
    val logger = LogManager.getLogger("todo")
    val todoList = TodoList()
    Vertx.vertx().deployVerticle(Verticle(todoList, logger))
}
