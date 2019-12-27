package info.ditrapani.familytodo

import android.nfc.Tag
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController

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
