package info.ditrapani.todo

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

data class Item(val author: String, val description: String, val status: Status) {
    fun toJson(): JsonObject {
        val jsonObj = json {
            obj(
                "author" to author,
                "description" to description,
                "status" to status.status
            )
        }
        if (status is Done) {
            jsonObj.put("worker", status.worker)
        }
        return jsonObj
    }
}

sealed class Status {
    abstract val status: String
}
object Todo : Status() {
    override val status = "todo"
}
data class Done(val worker: String) : Status() {
    override val status = "done"
}
