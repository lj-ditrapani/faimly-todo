package info.ditrapani.familytodo.additem

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import info.ditrapani.familytodo.R
import info.ditrapani.familytodo.model.TodoListFactory


class AddItemFragment : Fragment() {

    companion object {
        fun newInstance() = AddItemFragment()
    }

    private lateinit var viewModel: AddItemViewModel
    private lateinit var addView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addView = inflater.inflate(R.layout.add_item_fragment, container, false)
        return addView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        viewModel.construct(TodoListFactory.instance, addView.findNavController())
        val button = addView.findViewById<Button>(R.id.add_ok_button)
        button.setOnClickListener(::handleAdd)
    }

    fun handleAdd(@Suppress("UNUSED_PARAMETER") view: View) {
        val description = addView.findViewById<EditText>(R.id.add_item_description).text.toString()
        viewModel.handleAdd(description)
    }

}
