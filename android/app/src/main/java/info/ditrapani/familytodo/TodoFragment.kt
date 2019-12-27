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
        val viewManager = LinearLayoutManager(activity)
        val viewAdapter = RecyclerAdapter()
        todoView = inflater.inflate(R.layout.todo_fragment, container, false)
        todoView.findViewById<RecyclerView>(R.id.recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return todoView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        viewModel.construct(TodoListFactory.instance, todoView.findNavController())
        val addButton = todoView.findViewById<Button>(R.id.todo_add_item_button)
        addButton.setOnClickListener(::handleAdd)
        val cleanButton = todoView.findViewById<Button>(R.id.todo_clean_list_button)
        addButton.setOnClickListener(::handleClean)
    }

    fun handleAdd(view: View) {
        viewModel.goToAdd()
    }

    fun handleClean(view: View) {
        viewModel.cleanList()
    }

}

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    private val TAG = "TodoFragment"

    class Holder(private val item: View) : RecyclerView.ViewHolder(item) {
        fun setDescription(description: String) {
            val view = item.findViewById<TextView>(R.id.description)
            view.setText(description)
        }

        fun setAuthor(author: String) {
            val view = item.findViewById<TextView>(R.id.author)
            view.setText(author)
        }

        fun setWorker(worker: String) {
            val view = item.findViewById<TextView>(R.id.worker)
            view.setText(worker)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.d(TAG, "onCreateViewHolder")
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d(TAG, "onBindViewHolder called")
        holder.setDescription("Description  $position")
        holder.setAuthor("Author: $position")
        holder.setWorker("N/A")
    }
}
