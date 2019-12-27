package info.ditrapani.familytodo

sealed class Status
object Todo : Status()
data class Done(val worker: String) : Status()

data class TodoItem(
    val description: String,
    val author: String,
    val status: Status
) {
    fun getWorker(): String = when(status) {
        is Todo -> "..."
        is Done -> status.worker
    }
}