package info.ditrapani.todo

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

sealed class Result
data class Fail(val reason: String) : Result()
object Success : Result()

interface ITodoList {
    fun list(): JsonObject
    fun addItem(body: JsonObject): Result
    fun completeItem(id: String?): Result
    fun cleanList()
    fun prioritize(body: JsonObject): Result
}

class TodoList : ITodoList {
    val list = mutableListOf<Item>()

    override fun list(): JsonObject = json {
        obj("list" to JsonArray(list.map { it.toJson() }))
    }

    override fun addItem(body: JsonObject): Result {
        val item = Item(body.getString("author"), body.getString("description"), Todo)
        list += item
        return Success
    }

    override fun completeItem(id: String?): Result {
        return Success
    }

    override fun cleanList() {
    }

    override fun prioritize(body: JsonObject): Result {
        return Success
    }
}
