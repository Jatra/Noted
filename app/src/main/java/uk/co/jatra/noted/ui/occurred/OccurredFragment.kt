package uk.co.jatra.noted.ui.occurred

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.occurred_fragment.*
import uk.co.jatra.noted.R
import uk.co.jatra.noted.network.Occurrence

//INITIAL BASIC fragment created by Android Studio from ViewModel template
class OccurredFragment : Fragment() {

    companion object {
        fun newInstance() = OccurredFragment()
    }

    private lateinit var viewModel: OccurredViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.occurred_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OccurredViewModel::class.java)
        viewModel.occuredViewState.observe(this, Observer { updateView(it) })
    }

    //INITIAL very basic. Assumes always some data, and slaps into a single text field.
    private fun updateView(occurredViewState: OccurredViewState?) {
        occurredViewState?.let { viewState ->
            message.text = viewState.occurrences.joinToString(separator = "\n", transform = ::occurrenceText)
        }
    }

    private fun occurrenceText(occurrence: Occurrence) =
            with (occurrence) {
                "$time $user $what\n$detail"
            }
}
