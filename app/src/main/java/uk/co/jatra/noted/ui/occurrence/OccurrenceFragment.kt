package uk.co.jatra.noted.ui.occurrence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.occurrence.view.*
import kotlinx.android.synthetic.main.occurrence_fragment.view.*
import uk.co.jatra.noted.NotedApplication
import uk.co.jatra.noted.R
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.ui.NotedAdapter
import uk.co.jatra.noted.ui.NotedViewHolder
import uk.co.jatra.noted.ui.NotedViewModelFactory
import javax.inject.Inject

class OccurrenceFragment : Fragment() {

    private lateinit var viewModel: OccurrenceViewModel
    @Inject lateinit var viewModelFactory: NotedViewModelFactory<OccurrenceRequest, Occurrence, OccurrenceViewModel>
    private val adapter = NotedAdapter(R.layout.occurrence) {
        OccurrenceViewHolder(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getInjector().inject(this)
    }

    private fun getInjector() = (activity?.application as NotedApplication).appComponent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.occurrence_fragment, container, false)
        view.occurrencesView.adapter = adapter
        view.occurrencesView.layoutManager = LinearLayoutManager(context)
        view.ptr.setOnRefreshListener {
            viewModel.getData()
        }
        view.fab.setOnClickListener {
            activity?.let { host ->
                AddOccurrenceBottomSheet.newInstance(getInjector()).show(host.supportFragmentManager, "")
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OccurrenceViewModel::class.java)
        viewModel.occuredViewState.observe(this, Observer { updateView(it) })
    }

    private fun updateView(occurrenceViewState: OccurrenceViewState?) {
        occurrenceViewState?.let {
            adapter.setData(it.occurrences)
            view?.ptr?.isRefreshing = false
        }
    }
}

class OccurrenceViewHolder(itemView: View) : NotedViewHolder<Occurrence>(itemView) {
    override fun bind(item: Occurrence) {
        with(itemView) {
            whoView.text = item.user
            whenView.text = item.time
            dataView.text = item.what
        }
    }
}
