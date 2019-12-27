package info.ditrapani.familytodo

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class TodoViewModel : ViewModel() {
    lateinit private var todoSetup: ITodoSetup
    lateinit private var nav: NavController

    fun construct(todoSetup: ITodoSetup, nav: NavController) {
        this.todoSetup = todoSetup
        this.nav = nav
    }
}
