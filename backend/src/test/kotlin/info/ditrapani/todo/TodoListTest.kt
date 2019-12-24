package info.ditrapani.todo

import io.kotlintest.specs.FreeSpec
import io.vertx.core.json.JsonArray
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import kotlin.test.assertEquals

class TodoListTest : FreeSpec({
    "list" - {
        "returns json representation of the todo list" - {
            "for an empty list" {
                val actual = TodoList().list()
                val expected = json {
                    obj("list" to JsonArray())
                }
                assertEquals(expected, actual)
            }

            "for an list with some items" {
                val list = TodoList()
                val item1Json = json {
                    obj(
                        "author" to "John",
                        "description" to "Wash car"
                    )
                }
                val item2Json = json {
                    obj(
                        "author" to "Luke",
                        "description" to "Eat lunch"
                    )
                }
                list.addItem(item1Json)
                list.addItem(item2Json)

                val actual = list.list()

                val item1 = Item("John", "Wash car", Todo)
                val item2 = Item("Luke", "Eat lunch", Todo)
                val expected = json {
                    obj("list" to JsonArray(listOf(item1.toJson(), item2.toJson())))
                }
                assertEquals(expected, actual)
            }
        }
    }

    "addItem" - {
        "adds an item to the list" {
            val list = TodoList()
            val item1Json = json {
                obj(
                    "author" to "John",
                    "description" to "Wash car"
                )
            }
            val item2Json = json {
                obj(
                    "author" to "Luke",
                    "description" to "Eat lunch"
                )
            }

            list.addItem(item1Json)
            list.addItem(item2Json)

            val actual = list.list()
            val item1 = Item("John", "Wash car", Todo)
            val item2 = Item("Luke", "Eat lunch", Todo)
            val expected = json {
                obj("list" to JsonArray(listOf(item1.toJson(), item2.toJson())))
            }
            assertEquals(expected, actual)
        }
    }

    "completeItem" - {
        "when the item exists" - {
            "marks the item as done" {
                val list = TodoList()
                val item1Json = json {
                    obj(
                        "author" to "John",
                        "description" to "Wash car"
                    )
                }
                val item2Json = json {
                    obj(
                        "author" to "Luke",
                        "description" to "Eat lunch"
                    )
                }
                list.addItem(item1Json)
                list.addItem(item2Json)
                val jsonObj = json {
                    obj(
                        "itemIndex" to 1,
                        "worker" to "James"
                    )
                }

                val result = list.completeItem(jsonObj)

                assertEquals(Success, result)
                val actual = list.list()
                val item1 = Item("John", "Wash car", Todo)
                val item2 = Item("Luke", "Eat lunch", Done("James"))
                val expected = json {
                    obj("list" to JsonArray(listOf(item1.toJson(), item2.toJson())))
                }
                assertEquals(expected, actual)
            }
        }

        "when the item does not exist" - {
            "returns a Fail" {
                val list = TodoList()
                val item1Json = json {
                    obj(
                        "author" to "John",
                        "description" to "Wash car"
                    )
                }
                val item2Json = json {
                    obj(
                        "author" to "Luke",
                        "description" to "Eat lunch"
                    )
                }
                list.addItem(item1Json)
                list.addItem(item2Json)
                val jsonObj = json {
                    obj(
                        "itemIndex" to 2,
                        "worker" to "James"
                    )
                }

                val result = list.completeItem(jsonObj)

                assertEquals(Fail("itemIndex out of bounds"), result)
                val actual = list.list()
                val item1 = Item("John", "Wash car", Todo)
                val item2 = Item("Luke", "Eat lunch", Todo)
                val expected = json {
                    obj("list" to JsonArray(listOf(item1.toJson(), item2.toJson())))
                }
                assertEquals(expected, actual)
            }
        }
    }

    "cleanList" - {
        "removes completed items from the list" {
            val list = TodoList()
            val item1 = Item("Mark", "Mend nets", Done("James"))
            val item2 = Item("Luke", "Eat lunch", Todo)
            val item3 = Item("John", "Wash car", Done("Peter"))
            list.addItem(item1.toJson())
            list.addItem(item2.toJson())
            list.addItem(item3.toJson())
            val jsonObj1 = json {
                obj(
                    "itemIndex" to 0,
                    "worker" to "James"
                )
            }
            val jsonObj2 = json {
                obj(
                    "itemIndex" to 2,
                    "worker" to "Peter"
                )
            }
            list.completeItem(jsonObj1)
            list.completeItem(jsonObj2)
            list.cleanList()
            val actual = list.list()
            val expected = json {
                obj("list" to JsonArray(listOf(item2.toJson())))
            }
            assertEquals(expected, actual)
        }
    }

    "prioritize" - {
        "when the priority list is valid" - {
            "!re-sorts the list according to the new priority" {
            }
        }

        "when the priority list is invalid" - {
            "!returns a fail" {
            }
        }
    }
})
