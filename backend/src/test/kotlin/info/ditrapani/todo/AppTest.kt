package info.ditrapani.todo

import com.nhaarman.mockitokotlin2.mock
import io.kotlintest.specs.FreeSpec
import io.vertx.core.Vertx
import io.vertx.kotlin.core.deployVerticleAwait
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.Logger

class AppTest : FreeSpec({
    "can deploy the verticle" {
        val logger = mock<Logger>()
        val todoList = mock<ITodoList>()
        val vertx = Vertx.vertx()
        runBlocking {
            vertx.deployVerticleAwait(Verticle(todoList, logger))
        }
    }
})
