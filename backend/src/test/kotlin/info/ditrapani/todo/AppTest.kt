package info.ditrapani.todo

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.FreeSpec
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.core.deployVerticleAwait
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.ext.web.client.sendAwait
import io.vertx.kotlin.ext.web.client.sendJsonObjectAwait
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.Logger

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
                verify(todoList).list()
            }
        }
    }

    "/additem calls ITodoList.addItem() with payload and returns a 200" {
        with(Fixture()) {
            val jsonObj = json {
                obj(
                    "author" to "John",
                    "description" to "Wash car"
                )
            }
            testServer {
                val response = client
                    .post(PORT, "localhost", "/additem")
                    .sendJsonObjectAwait(jsonObj)
                assertEquals(200, response.statusCode())
                assertEquals("item added", response.bodyAsString())
                verify(todoList).addItem(jsonObj)
            }
        }
    }

    "/done/:itemId calls ITodoList.completeItem with itemId and returns the result" {
        with(Fixture()) {
            testServer {
                val response = client
                    .post(PORT, "localhost", "/done/3")
                    .sendAwait()
                assertEquals(200, response.statusCode())
                assertEquals("item complete", response.bodyAsString())
                verify(todoList).completeItem("3")
            }
        }
    }

    "/clean calls ITodoList.clean" {
        with(Fixture()) {
            testServer {
                val response = client
                    .post(PORT, "localhost", "/clean")
                    .sendAwait()
                assertEquals(200, response.statusCode())
                assertEquals("list cleaned", response.bodyAsString())
                verify(todoList).cleanList()
            }
        }
    }

    "/prioritize ITodoList.prioritize() with payload and returns a 200" {
        with(Fixture()) {
            val jsonObj = json {
                obj(
                    "priority" to listOf(4, 2, 1, 3)
                )
            }
            testServer {
                val response = client
                    .post(PORT, "localhost", "/prioritize")
                    .sendJsonObjectAwait(jsonObj)
                assertEquals(200, response.statusCode())
                assertEquals("prioritized", response.bodyAsString())
                verify(todoList).prioritize(jsonObj)
            }
        }
    }
})
