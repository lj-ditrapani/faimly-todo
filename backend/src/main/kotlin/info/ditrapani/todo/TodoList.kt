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
    fun addItem(body: JsonObject): Unit
    fun completeItem(body: JsonObject): Result
    fun cleanList(): Unit
    fun prioritize(body: JsonObject): Result
}

class TodoList : ITodoList {
    val list = mutableListOf<Item>()

    override fun list(): JsonObject = json {
        obj("list" to JsonArray(list.map { it.toJson() }))
    }

    override fun addItem(body: JsonObject) {
        val item = Item(body.getString("author"), body.getString("description"), Todo)
        list += item
    }

    override fun completeItem(body: JsonObject): Result {
        val index = body.getInteger("itemIndex")
        val worker = body.getString("worker")
        val maybeItem = list.getOrNull(index)
        if (maybeItem == null) {
            return Fail("itemIndex out of bounds")
        } else {
            list[index] = maybeItem.copy(status = Done(worker))
            return Success
        }
    }

    override fun cleanList() {
        val newList = list.filter { it.status is Todo }
        list.clear()
        newList.forEach {
            list += it
        }
    }

    override fun prioritize(body: JsonObject): Result {
        val jArray = body.getJsonArray("priority")
        val priority = ArrayList<Int>(jArray.size())
        for (i in 0.until(jArray.size())) {
            priority += jArray.getInteger(i)
        }
        if (priority.sorted() == 0.until(list.size).toList()) {
            val newList = ArrayList<Item>(list.size)
            for (i in priority) {
                newList.add(list[i])
            }
            list.clear()
            newList.forEach {
                list += it
            }
            return Success
        } else {
            return Fail("Priorty list is invalid")
        }
    }
}
