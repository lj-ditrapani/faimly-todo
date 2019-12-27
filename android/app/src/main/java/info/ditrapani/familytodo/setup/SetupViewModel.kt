package info.ditrapani.familytodo.setup

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import info.ditrapani.familytodo.model.ITodoSetup

class SetupViewModel() : ViewModel() {
    private val TAG = "SetupViewModel"
    lateinit private var todoSetup: ITodoSetup
    lateinit private var nav: NavController

    fun construct(todoSetup: ITodoSetup, nav: NavController) {
        this.todoSetup = todoSetup
        this.nav = nav
    }

    fun setup(userName: String, serverUrl: String) {
        todoSetup.setup(userName, serverUrl)
        nav.navigate(
            SetupFragmentDirections.actionSetupFragmentToTodoFragment()
        )
    }
}
