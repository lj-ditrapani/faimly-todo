package info.ditrapani.familytodo

sealed class Result
data class Fail(val reason: String) : Result()
object Success : Result()

interface ITodoList {
    fun list(): List<TodoItem>
    fun completeItem(itemIndex: Int): Result
    fun cleanList(): Unit
}

interface ITodoAdder {
    fun addItem(description: String): Unit
}

interface ITodoSetup {
    fun setup(userName: String, serverUrl: String)
}

interface ITodoPrioritize {
    fun list(): List<TodoItem>
    fun prioritize(priority: List<Int>): Result
}

class TodoList : ITodoSetup, ITodoList, ITodoAdder, ITodoPrioritize {
    lateinit private var userName: String
    lateinit private var serverUrl: String
    private val list = mutableListOf<TodoItem>()

    override fun setup(userName: String, serverUrl: String) {
        this.userName = userName
        this.serverUrl = serverUrl
    }

    override fun list(): List<TodoItem> = list

    override fun addItem(description: String) {
        list += TodoItem(description, userName, Todo)
    }

    override fun completeItem(itemIndex: Int): Result {
        list[itemIndex] = list[itemIndex].copy(status = Done(userName))
        return Success
    }

    override fun cleanList() {
        val newList = list.filter { it.status is Todo }
        list.clear()
        newList.forEach {
            list += it
        }
    }

    override fun prioritize(priority: List<Int>): Result =
        if (priority.sorted() == 0.until(list.size).toList()) {
            val newList = ArrayList<TodoItem>(list.size)
            for (i in priority) {
                newList.add(list[i])
            }
            list.clear()
            newList.forEach {
                list += it
            }
            Success
        } else {
            Fail("Priorty list is invalid")
        }
}

object TodoListFactory {
    val instance = TodoList()
}