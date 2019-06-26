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
import kotlinx.android.synthetic.main.occurrence.view.idView
import uk.co.jatra.noted.NotedApplication
import uk.co.jatra.noted.R
import uk.co.jatra.noted.network.User
import uk.co.jatra.noted.network.UserRequest
import uk.co.jatra.noted.ui.Adapter
import uk.co.jatra.noted.ui.NotedViewModelFactory
import uk.co.jatra.noted.ui.ViewHolder
import javax.inject.Inject

class UserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    @Inject
    lateinit var viewModelFactory: NotedViewModelFactory<UserRequest, User, UserViewModel>
    private val adapter = Adapter(R.layout.event) {
        UserViewHolder(it)
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.eventViewState.observe(this, Observer { updateView(it) })
    }

    private fun updateView(eventViewState: UserViewState?) {
        eventViewState?.let {
            adapter.setData(it.events)
            view?.ptr?.isRefreshing = false
        }
    }
}


class UserViewHolder(itemView: View) : ViewHolder<User>(itemView) {
    override fun bind(item: User) {
        with(itemView) {
            idView.text = item.id
            nameView.text = item.name
            descriptionView.text = item.notes
        }
    }
}
