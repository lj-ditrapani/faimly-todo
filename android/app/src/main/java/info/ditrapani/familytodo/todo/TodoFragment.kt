package info.ditrapani.familytodo.todo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.ditrapani.familytodo.R
import info.ditrapani.familytodo.model.TodoListFactory

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
        viewModel.construct(
            TodoListFactory.instance,
            todoView.findNavController()
        )
        val viewManager = LinearLayoutManager(activity)
        todoView.findViewById<RecyclerView>(R.id.todo_recycler).apply {
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
        val orderButton = todoView.findViewById<Button>(R.id.todo_order_button)
        orderButton.setOnClickListener(::handleOrder)
        val refreshButton = todoView.findViewById<Button>(R.id.todo_refresh_button)
        refreshButton.setOnClickListener(::handleRefresh)
    }

    fun handleSetup(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModel.goToSetup()
    }

    fun handleAdd(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModel.goToAdd()
    }

    fun handleClean(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModel.cleanList()
    }

    fun handleOrder(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModel.goToOrder()
    }

    fun handleRefresh(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModel.refresh()
    }
}
