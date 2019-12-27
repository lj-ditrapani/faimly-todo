package info.ditrapani.familytodo.order

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


class OrderFragment : Fragment() {

    companion object {
        fun newInstance() = OrderFragment()
    }

    private lateinit var orderView: View
    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderView = inflater.inflate(R.layout.order_fragment, container, false)
        return orderView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        viewModel.construct(
            TodoListFactory.instance,
            orderView.findNavController()
        )
        val viewManager = LinearLayoutManager(activity)
        orderView.findViewById<RecyclerView>(R.id.order_recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewModel.getRecyclerAdapter()
        }
        val okButton = orderView.findViewById<Button>(R.id.order_ok_button)
        okButton.setOnClickListener(::handleOk)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLatestList()
    }

    fun handleOk(@Suppress("UNUSED_PARAMETER")view: View) {
        viewModel.doOrdering()
    }
}
