package info.ditrapani.todo

import io.kotlintest.specs.FreeSpec
import io.vertx.core.json.JsonObject
import kotlin.test.assertEquals

class ItemTest : FreeSpec({
    "toJson" - {
        "returns json representation of Item" {
            val actual = Item("John", "Wash car", Todo).toJson()
            val expected = JsonObject()
            assertEquals(expected, actual)
        }
    }
})
