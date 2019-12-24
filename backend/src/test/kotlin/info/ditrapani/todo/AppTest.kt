package info.ditrapani.todo

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.FreeSpec
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.ext.web.client.sendAwait
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.Logger
// sendJsonObjectAwait

class AppTest : FreeSpec({
    class Fixture {
        val logger = mock<Logger>()
        val todoList = mock<ITodoList>()
        val vertx = Vertx.vertx()
        val client = WebClient.create(vertx)

        fun testServer(f: suspend Fixture.() -> Unit) {
            runBlocking {
                vertx.deployVerticleAwait(Verticle(todoList, logger))
                f()
            }
            vertx.close()
        }
    }

    "/list calls ITodoList.list() and returns the resulting json" {
        with(Fixture()) {
            val jsonObj = json {
                obj("list" to listOf<String>())
            }
            whenever(todoList.list()).thenReturn(jsonObj)
            testServer {
                val response = client.get(PORT, "localhost", "/list").sendAwait()
                assertEquals(
                    "application/json; charset=utf-8",
                    response.getHeader("content-type")
                )
                assertEquals(200, response.statusCode())
                assertEquals(jsonObj, response.bodyAsJsonObject())
            }
        }
    }
})
