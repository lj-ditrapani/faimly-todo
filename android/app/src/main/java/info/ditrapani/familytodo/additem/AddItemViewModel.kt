package info.ditrapani.familytodo.additem

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import info.ditrapani.familytodo.model.ITodoAdder

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
