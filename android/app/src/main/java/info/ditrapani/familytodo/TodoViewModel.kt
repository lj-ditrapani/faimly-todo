package info.ditrapani.familytodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView

class TodoViewModel : ViewModel() {
    lateinit private var todoList: ITodoList
    lateinit private var nav: NavController
    lateinit private var recyclerAdapter: RecyclerAdapter

    fun construct(
        todoList: ITodoList,
        nav: NavController
    ) {
        this.todoList = todoList
        this.nav = nav
        this.recyclerAdapter = RecyclerAdapter(todoList.list())
    }

    fun getRecyclerAdapter(): RecyclerAdapter = recyclerAdapter

    fun goToSetup() {
        nav.navigate(
            TodoFragmentDirections.actionTodoFragmentToSetupFragment()
        )
    }

    fun goToAdd() {
        nav.navigate(
            TodoFragmentDirections.actionTodoFragmentToAddItemFragment()
        )
    }

    fun cleanList() {
        todoList.cleanList()
        recyclerAdapter.notifyDataSetChanged()
    }

    fun refresh() {
        recyclerAdapter.notifyDataSetChanged()
    }
}

class RecyclerAdapter(val list: List<TodoItem>) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
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

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d(TAG, "onBindViewHolder called")
        val item = list[position]
        holder.setDescription(item.description)
        holder.setAuthor(item.author)
        holder.setWorker(item.getWorker())
    }
}
