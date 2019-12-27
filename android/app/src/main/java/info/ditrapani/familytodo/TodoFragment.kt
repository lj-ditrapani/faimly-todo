package info.ditrapani.familytodo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TodoFragment : Fragment() {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private lateinit var todoView: View
    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        todoView = inflater.inflate(R.layout.todo_fragment, container, false)
        return todoView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        val viewManager = LinearLayoutManager(activity)
        viewModel.construct(
            TodoListFactory.instance,
            todoView.findNavController()
        )
        todoView.findViewById<RecyclerView>(R.id.recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewModel.getRecyclerAdapter()
        }
        val setupButton = todoView.findViewById<Button>(R.id.todo_setup_button)
        setupButton.setOnClickListener(::handleSetup)
        val addButton = todoView.findViewById<Button>(R.id.todo_add_item_button)
        addButton.setOnClickListener(::handleAdd)
        val cleanButton = todoView.findViewById<Button>(R.id.todo_clean_list_button)
        cleanButton.setOnClickListener(::handleClean)
        val refreshButton = todoView.findViewById<Button>(R.id.todo_refresh_button)
        refreshButton.setOnClickListener(::handleRefresh)
    }

    fun handleSetup(view: View) {
        viewModel.goToSetup()
    }

    fun handleAdd(view: View) {
        viewModel.goToAdd()
    }

    fun handleClean(view: View) {
        viewModel.cleanList()
    }

    fun handleRefresh(view: View) {
        viewModel.refresh()
    }
}
