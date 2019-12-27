package info.ditrapani.familytodo

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class TodoViewModel : ViewModel() {
    lateinit private var todoList: ITodoList
    lateinit private var nav: NavController

    fun construct(todoList: ITodoList, nav: NavController) {
        this.todoList = todoList
        this.nav = nav
    }

    fun goToAdd() {
        nav.navigate(
            TodoFragmentDirections.actionTodoFragmentToAddItemFragment()
        )
    }

    fun cleanList() {
        todoList.cleanList()
    }
}
