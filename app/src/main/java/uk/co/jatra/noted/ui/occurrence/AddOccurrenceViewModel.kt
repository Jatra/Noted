package uk.co.jatra.noted.ui.occurrence

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.network.Event
import uk.co.jatra.noted.network.EventRequest
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.repository.Repository
import uk.co.jatra.noted.ui.event.EventViewState
import javax.inject.Inject
import javax.inject.Named

class AddOccurrenceViewModel
    @Inject constructor(
        private val occurrenceRepository: Repository<OccurrenceRequest, Occurrence>,
        private val eventRepository: Repository<EventRequest, Event>,
        @Named("Main") val mainThreadScheduler: Scheduler
    ): ViewModel() {

    val addOccurenceViewState: MutableLiveData<EventViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()
    val done: MutableLiveData<OneShot> = MutableLiveData()

    //INITIAL. Always makes one request at start, no arguments.
    //TODO add some querying.
    init {
        getData()
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun getData() {
        subscriptions.add(
            eventRepository.getData()
                .observeOn(mainThreadScheduler)
                .subscribe({ value ->
                    addOccurenceViewState.setValue(EventViewState(value))
                }, {
                    //viewstate should have something for errors
                    addOccurenceViewState.value = EventViewState(emptyList())
                }
                )
        )
    }

    fun addOccurrence(event: Event, userId: String) {
        subscriptions.add(
            occurrenceRepository.addItem(OccurrenceRequest(userId, event.id))
                .subscribe({
                    Log.d("AddOccurrence", "Success $it")
                    done()
                }, {
                    Log.e("AddOccurrence", "Error", it)
                })
        )
    }

    private fun done() {
        done.postValue(OneShot())
    }
}

class OneShot {
    private var handled = false

    fun isHandled(): Boolean {
        val previous = handled
        handled = true
        return previous
    }

    fun unHandled() = !isHandled()
}