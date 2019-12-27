package info.ditrapani.familytodo.order

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import info.ditrapani.familytodo.R
import info.ditrapani.familytodo.model.Done
import info.ditrapani.familytodo.model.ITodoOrder
import info.ditrapani.familytodo.model.IndexedTodoItem
import info.ditrapani.familytodo.model.Todo

class OrderViewModel : ViewModel() {
    lateinit private var todoOrder: ITodoOrder
    lateinit private var nav: NavController
    lateinit private var recyclerAdapter: RecyclerAdapter
    private val list = mutableListOf<IndexedTodoItem>()

    fun construct(
        todoOrder: ITodoOrder,
        nav: NavController
    ) {
        this.todoOrder = todoOrder
        this.nav = nav
        getLatestList()
        this.recyclerAdapter = RecyclerAdapter(list, ::itemUp, ::itemDown)
    }

    fun getLatestList() {
        list.clear()
        list.addAll(
            todoOrder.list().mapIndexed { index, item ->
                IndexedTodoItem(index, item)
            }
        )
    }

    fun updateToLatestList() {
        getLatestList()
        recyclerAdapter.notifyDataSetChanged()
    }

    fun getRecyclerAdapter(): RecyclerAdapter = recyclerAdapter

    fun doOrdering() {
        todoOrder.order(list.map { it.index})
        nav.navigate(
            OrderFragmentDirections.actionOrderFragmentToTodoFragment()
        )
    }

    fun itemUp(index: Int) {
        val item = list.removeAt(index)
        list.add(index - 1, item)
        recyclerAdapter.notifyItemMoved(index, index - 1)
        recyclerAdapter.notifyItemRangeChanged(index - 1, 2)
    }

    fun itemDown(index: Int) {
        val item = list.removeAt(index)
        list.add(index + 1, item)
        recyclerAdapter.notifyItemMoved(index, index + 1)
        recyclerAdapter.notifyItemRangeChanged(index, 2)
    }
}

class RecyclerHolder(
    private val item: View,
    private val itemUp: (Int) -> Unit,
    private val itemDown: (Int) -> Unit
) : RecyclerView.ViewHolder(item) {
    init {
        val upButton = item.findViewById<Button>(R.id.up_button)
        val downButton = item.findViewById<Button>(R.id.down_button)
        upButton.setOnClickListener {
            itemUp(adapterPosition)
        }
        downButton.setOnClickListener {
            itemDown(adapterPosition)
        }
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
        view.setEnabled(false)
    }

    fun checkBounds(position: Int, size: Int) {
        val upButton = item.findViewById<Button>(R.id.up_button)
        val downButton = item.findViewById<Button>(R.id.down_button)
        if (position == 0) {
            upButton.setEnabled(false)
        } else {
            upButton.setEnabled(true)
        }
        if (position == size - 1) {
            downButton.setEnabled(false)
        } else {
            downButton.setEnabled(true)
        }
    }
}

class RecyclerAdapter(
    private val list: List<IndexedTodoItem>,
    private val itemUpClicked: (Int) -> Unit,
    private val itemDownCilcked: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerHolder>() {
    private val TAG = "order.RecyclerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.indexed_todo_item, parent, false)
        return RecyclerHolder(view, itemUpClicked, itemDownCilcked)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder called")
        val indexedItem = list[position]
        val item = indexedItem.item
        holder.setDescription(item.description)
        holder.setAuthor(item.author)
        val isCompleted = when(item.status) {
            is Todo -> false
            is Done -> true
        }
        holder.setCompleted(isCompleted)
        holder.checkBounds(position, list.size)
    }
}
