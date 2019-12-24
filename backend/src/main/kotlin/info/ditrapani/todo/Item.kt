package info.ditrapani.todo

import io.vertx.core.json.JsonObject

data class Item(val author: String, val description: String, val status: Status) {
    fun toJson(): JsonObject {
        return JsonObject()
    }
}

sealed class Status
object Todo : Status()
data class Done(val worker: String) : Status()
