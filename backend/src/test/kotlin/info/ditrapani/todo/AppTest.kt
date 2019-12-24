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
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.Logger

class AppTest : FreeSpec({
    "can deploy the verticle" {
        val logger = mock<Logger>()
        val todoList = mock<ITodoList>()
        val vertx = Vertx.vertx()
        val jsonObj = json {
            obj("list" to listOf<String>())
        }
        whenever(todoList.list()).thenReturn(jsonObj)
        runBlocking {
            vertx.deployVerticleAwait(Verticle(todoList, logger))
            val client = WebClient.create(vertx)
            val response = client.get(PORT, "localhost", "/list").sendAwait()
            println(response)
            println(response.statusCode())
            println(response.bodyAsJsonObject())
        }
    }
})
