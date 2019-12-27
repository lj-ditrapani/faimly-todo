package info.ditrapani.familytodo

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class AddItemViewModel : ViewModel() {
    lateinit private var todoAdder : ITodoAdder
    lateinit private var nav: NavController

    fun construct(todoAdder: ITodoAdder, nav: NavController) {
        this.todoAdder = todoAdder
        this.nav = nav
    }

    fun handleAdd(description: String) {
        todoAdder.addItem(description)
        nav.navigate(AddItemFragmentDirections.actionAddItemFragmentToTodoFragment())
    }
}
