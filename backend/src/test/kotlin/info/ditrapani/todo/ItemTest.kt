package info.ditrapani.todo

import io.kotlintest.specs.FreeSpec
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import kotlin.test.assertEquals

class ItemTest : FreeSpec({
    "toJson" - {
        "returns json representation of Item" - {
            "when the status is todo" {
                val actual = Item("John", "Wash car", Todo).toJson()
                val expected = json {
                    obj(
                        "author" to "John",
                        "description" to "Wash car",
                        "status" to "todo"
                    )
                }
                assertEquals(expected, actual)
            }

            "when the status is done" {
                val actual = Item("John", "Wash car", Done("Luke")).toJson()
                val expected = json {
                    obj(
                        "author" to "John",
                        "description" to "Wash car",
                        "status" to "done",
                        "worker" to "Luke"
                    )
                }
                assertEquals(expected, actual)
            }
        }
    }
})
