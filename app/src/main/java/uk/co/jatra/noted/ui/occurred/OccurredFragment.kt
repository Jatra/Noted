package uk.co.jatra.noted.ui.occurred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.occurred_fragment.view.*
import uk.co.jatra.noted.NotedApplication
import uk.co.jatra.noted.R
import javax.inject.Inject

//INITIAL BASIC fragment created by Android Studio from ViewModel template
class OccurredFragment : Fragment() {

    companion object {
        fun newInstance() = OccurredFragment()
    }

    private lateinit var viewModel: OccurredViewModel
    @Inject
    lateinit var viewModelFactory: OccurrenceViewModelFactory
    private val adapter = OccurredAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getInjector().inject(this)
    }

    private fun getInjector() = (activity?.application as NotedApplication).appComponent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.occurred_fragment, container, false)
        view.occurrencesView.adapter = adapter
        view.occurrencesView.layoutManager = LinearLayoutManager(context)
        view.ptr.setOnRefreshListener {
            viewModel.getData()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OccurredViewModel::class.java)
        viewModel.occuredViewState.observe(this, Observer { updateView(it) })
    }

    //INITIAL very basic. Assumes always some data, and slaps into a single text field.
    private fun updateView(occurredViewState: OccurredViewState?) {
        occurredViewState?.let {
            adapter.setData(it.occurrences)
            view?.ptr?.isRefreshing = false
        }
    }

}
