package uk.co.jatra.noted.ui.occurrence

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_occurrence_dialog.view.*
import uk.co.jatra.noted.R
import uk.co.jatra.noted.di.AppComponent
import uk.co.jatra.noted.network.Event
import uk.co.jatra.noted.ui.event.EventViewState
import javax.inject.Inject

class AddOccurrenceBottomSheet : BottomSheetDialogFragment() {

    @Inject lateinit var addOccurrenceViewModelFactory: AddOccurrenceViewModelFactory
    lateinit var viewModel: AddOccurrenceViewModel
    lateinit var adapter: EventSpinnerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.add_occurrence_dialog, null)
        view.addButton.setOnClickListener {
            viewModel.addOccurrence(view.spinner.selectedItem as Event, "1")
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this, addOccurrenceViewModelFactory).get(AddOccurrenceViewModel::class.java)
        viewModel.addOccurenceViewState.observe(this, Observer { updateView(it) })
    }

    private fun updateView(eventViewState: EventViewState?) {
        eventViewState?.let {
            val eventNames = it.events.map { event -> event.eventName }
            Log.d("SHEET", eventNames.toString())
            adapter = EventSpinnerAdapter(context!!, it.events)
            view?.spinner?.adapter = adapter
        }
    }

    companion object {
        fun newInstance(appComponent: AppComponent): AddOccurrenceBottomSheet {
            val bottomSheet = AddOccurrenceBottomSheet()
            appComponent.inject(bottomSheet)
            return bottomSheet
        }
    }
}


class EventSpinnerAdapter(context: Context, events: List<Event>): ArrayAdapter<Event>(context, R.layout.spinner_item, events) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)?.eventName ?: ""
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.eventName ?: ""
        return view
    }
}
