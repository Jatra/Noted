package uk.co.jatra.noted.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.user.view.*
import kotlinx.android.synthetic.main.user_fragment.view.*
import uk.co.jatra.noted.NotedApplication
import uk.co.jatra.noted.R
import uk.co.jatra.noted.model.User
import uk.co.jatra.noted.ui.NotedAdapter
import uk.co.jatra.noted.ui.NotedViewHolder
import javax.inject.Inject

class UserFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    private val adapter = NotedAdapter(R.layout.user) {
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
        val view = inflater.inflate(R.layout.user_fragment, container, false)
        view.usersView.adapter = adapter
        view.usersView.layoutManager = LinearLayoutManager(context)
        view.ptr.setOnRefreshListener {
            viewModel.getData()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        viewModel.userViewState.observe(this, Observer { updateView(it) })
    }

    private fun updateView(userViewState: UserViewState?) {
        userViewState?.let {
            adapter.setData(it.users)
            view?.ptr?.isRefreshing = false
        }
    }
}

class UserViewHolder(itemView: View) : NotedViewHolder<User>(itemView) {
    override fun bind(item: User) {
        with(itemView) {
            idView.text = item.id.toString()
            nameView.text = item.name
            notesView.text = item.notes
        }
    }
}
