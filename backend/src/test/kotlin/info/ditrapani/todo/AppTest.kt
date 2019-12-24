package info.ditrapani.todo

import com.nhaarman.mockitokotlin2.any
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
            try {
                runBlocking {
                    vertx.deployVerticleAwait(Verticle(todoList, logger))
                    f()
                }
            } finally {
                vertx.close()
            }
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

    "/add calls ITodoList.addItem() with payload and returns a 200" {
        with(Fixture()) {
            val jsonObj = json {
                obj(
                    "author" to "John",
                    "description" to "Wash car"
                )
            }
            testServer {
                val response = client
                    .post(PORT, "localhost", "/add")
                    .sendJsonObjectAwait(jsonObj)
                assertEquals(200, response.statusCode())
                assertEquals("item added", response.bodyAsString())
                verify(todoList).addItem(jsonObj)
            }
        }
    }

    "/complete calls ITodoList.completeItem with payload and returns the result" - {
        "when the TodoList returs a Success, it returns a 200" {
            with(Fixture()) {
                val jsonObj = json {
                    obj(
                        "itemIndex" to 3,
                        "worker" to "Luke"
                    )
                }
                whenever(todoList.completeItem(any())).thenReturn(Success)
                testServer {
                    val response = client
                        .post(PORT, "localhost", "/complete")
                        .sendJsonObjectAwait(jsonObj)
                    assertEquals(200, response.statusCode())
                    assertEquals("item complete", response.bodyAsString())
                    verify(todoList).completeItem(jsonObj)
                }
            }
        }

        "when the TodoList returs a Fail, it returns a 400" {
            with(Fixture()) {
                val jsonObj = json {
                    obj(
                        "itemIndex" to 3,
                        "worker" to "Luke"
                    )
                }
                whenever(todoList.completeItem(any()))
                    .thenReturn(Fail("itemIndex out of bounds"))
                testServer {
                    val response = client
                        .post(PORT, "localhost", "/complete")
                        .sendJsonObjectAwait(jsonObj)
                    assertEquals(400, response.statusCode())
                    assertEquals("itemIndex out of bounds", response.bodyAsString())
                    verify(todoList).completeItem(jsonObj)
                }
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

    "/prioritize ITodoList.prioritize() with payload and returns the result" - {
        "when the priority list is valid, returns a 200" {
            with(Fixture()) {
                val jsonObj = json {
                    obj(
                        "priority" to listOf(4, 2, 1, 3)
                    )
                }
                whenever(todoList.prioritize(any())).thenReturn(Success)
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

        "when the priority list is invalid, returns a 400" {
            with(Fixture()) {
                val jsonObj = json {
                    obj(
                        "priority" to listOf(4, 2, 1, 3)
                    )
                }
                whenever(todoList.prioritize(any()))
                    .thenReturn(Fail("Priorty list is invalid"))
                testServer {
                    val response = client
                        .post(PORT, "localhost", "/prioritize")
                        .sendJsonObjectAwait(jsonObj)
                    assertEquals(400, response.statusCode())
                    assertEquals("Priorty list is invalid", response.bodyAsString())
                    verify(todoList).prioritize(jsonObj)
                }
            }
        }
    }
})
