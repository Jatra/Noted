package uk.co.jatra.noted.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.event.view.*
import kotlinx.android.synthetic.main.event_fragment.view.*
import uk.co.jatra.noted.NotedApplication
import uk.co.jatra.noted.R
import uk.co.jatra.noted.network.Event
import uk.co.jatra.noted.network.EventRequest
import uk.co.jatra.noted.ui.NotedAdapter
import uk.co.jatra.noted.ui.NotedViewHolder
import uk.co.jatra.noted.ui.NotedViewModelFactory
import javax.inject.Inject

class EventFragment : Fragment() {

    private lateinit var viewModel: EventViewModel
    @Inject
    lateinit var viewModelFactory: NotedViewModelFactory<EventRequest, Event, EventViewModel>
    private val adapter = NotedAdapter(R.layout.event) {
        EventViewHolder(it)
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
        val view = inflater.inflate(R.layout.event_fragment, container, false)
        view.eventsView.adapter = adapter
        view.eventsView.layoutManager = LinearLayoutManager(context)
        view.ptr.setOnRefreshListener {
            viewModel.getData()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
        viewModel.eventViewState.observe(this, Observer { updateView(it) })
    }

    private fun updateView(eventViewState: EventViewState?) {
        eventViewState?.let {
            adapter.setData(it.events)
            view?.ptr?.isRefreshing = false
        }
    }
}


class EventViewHolder(itemView: View) : NotedViewHolder<Event>(itemView) {
    override fun bind(item: Event) {
        with(itemView) {
            idView.text = item.id
            nameView.text = item.eventName
            descriptionView.text = item.eventDescription
        }
    }
}
