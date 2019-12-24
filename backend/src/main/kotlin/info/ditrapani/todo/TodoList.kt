package info.ditrapani.todo

import io.vertx.core.json.JsonObject

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
    override fun list(): JsonObject {
        return JsonObject()
    }

    override fun addItem(body: JsonObject): Result {
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
