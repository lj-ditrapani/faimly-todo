package info.ditrapani.familytodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
        this.recyclerAdapter = RecyclerAdapter(todoList.list(), ::itemDone)
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

    fun itemDone(position: Int) {
        todoList.completeItem(position)
        recyclerAdapter.notifyItemChanged(position)
    }
}

class RecyclerHolder(
    private val item: View,
    private val itemDoneClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(item), View.OnClickListener {
    init {
        val checkBox = item.findViewById<CheckBox>(R.id.done)
        checkBox.setOnClickListener(this)
    }

    fun setDescription(description: String) {
        val view = item.findViewById<TextView>(R.id.description)
        view.setText(description)
    }

    fun setAuthor(author: String) {
        val view = item.findViewById<TextView>(R.id.author)
        view.setText(author)
    }

    fun setCompleted(isCompleted: Boolean) {
        val view = item.findViewById<CheckBox>(R.id.done)
        view.setChecked(isCompleted)
    }

    fun setWorker(worker: String) {
        val view = item.findViewById<TextView>(R.id.worker)
        view.setText(worker)
    }

    override fun onClick(v: View?) {
        itemDoneClicked(adapterPosition)
    }
}

class RecyclerAdapter(
    private val list: List<TodoItem>,
    private val itemDone: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerHolder>() {
    private val TAG = "TodoFragment"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return RecyclerHolder(view, itemDone)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder called")
        val item = list[position]
        holder.setDescription(item.description)
        holder.setAuthor(item.author)
        val isCompleted = when(item.status) {
            is Todo -> false
            is Done -> true
        }
        holder.setCompleted(isCompleted)
        holder.setWorker(item.getWorker())
    }
}
