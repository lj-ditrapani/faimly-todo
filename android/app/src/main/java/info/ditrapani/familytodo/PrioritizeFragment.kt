package info.ditrapani.familytodo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class PrioritizeFragment : Fragment() {

    companion object {
        fun newInstance() = PrioritizeFragment()
    }

    private lateinit var viewModel: PrioritizeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.prioritize_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PrioritizeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
