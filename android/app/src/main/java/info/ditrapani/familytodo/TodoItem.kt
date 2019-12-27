package info.ditrapani.familytodo

sealed class Status
object Todo : Status()
data class Done(val worker: String) : Status()

data class TodoItem(
    private val description: String,
    private val author: String,
    val status: Status
) {
    fun getWorker(): String = when(status) {
        is Todo -> "..."
        is Done -> status.worker
    }
}