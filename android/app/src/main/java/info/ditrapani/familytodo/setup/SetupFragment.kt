package info.ditrapani.familytodo.setup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import info.ditrapani.familytodo.R
import info.ditrapani.familytodo.model.TodoListFactory

class SetupFragment : Fragment() {
    private val TAG = "SetupFrag"

    companion object {
        fun newInstance() = SetupFragment()
    }

    private lateinit var setupView: View
    private lateinit var viewModel: SetupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        setupView = inflater.inflate(R.layout.setup_fragment, container, false)
        return setupView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SetupViewModel::class.java)
        viewModel.construct(TodoListFactory.instance, setupView.findNavController())
        val button = setupView.findViewById<Button>(R.id.setup_ok_button)
        button.setOnClickListener(::handleSetup)
    }

    override fun onResume() {
        super.onResume()
        val userName = setupView.findViewById<EditText>(R.id.user_name)
        val serverUrl = setupView.findViewById<EditText>(R.id.server_url)
        viewModel.setupFields(userName, serverUrl)
    }

    fun handleSetup(@Suppress("UNUSED_PARAMETER") view: View) {
        val userName = setupView.findViewById<EditText>(R.id.user_name).text.toString()
        val serverUrl = setupView.findViewById<EditText>(R.id.server_url).text.toString()
        viewModel.setup(userName, serverUrl)
    }
}
