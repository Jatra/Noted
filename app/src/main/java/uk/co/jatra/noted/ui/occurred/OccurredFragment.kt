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
import kotlinx.android.synthetic.main.occurrence.view.*
import uk.co.jatra.noted.NotedApplication
import uk.co.jatra.noted.R
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.ui.Adapter
import uk.co.jatra.noted.ui.NotedViewModelFactory
import uk.co.jatra.noted.ui.ViewHolder
import javax.inject.Inject

class OccurredFragment : Fragment() {

    private lateinit var viewModel: OccurredViewModel
    @Inject
    lateinit var viewModelFactory: NotedViewModelFactory<OccurrenceRequest, Occurrence, OccurredViewModel>
    private val adapter = Adapter(R.layout.occurrence) {
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

    private fun updateView(occurredViewState: OccurredViewState?) {
        occurredViewState?.let {
            adapter.setData(it.occurrences)
            view?.ptr?.isRefreshing = false
        }
    }
}

class OccurrenceViewHolder(itemView: View) : ViewHolder<Occurrence>(itemView) {
    override fun bind(item: Occurrence) {
        with(itemView) {
            whoView.text = item.user
            whenView.text = item.time
            idView.text = item.id
            dataView.text = item.what
        }
    }
}
