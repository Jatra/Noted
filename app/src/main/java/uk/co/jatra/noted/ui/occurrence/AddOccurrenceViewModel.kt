package uk.co.jatra.noted.ui.occurrence

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.model.Event
import uk.co.jatra.noted.model.Occurrence
import uk.co.jatra.noted.model.User
import uk.co.jatra.noted.repository.EventRepository
import uk.co.jatra.noted.repository.OccurrenceRepository
import uk.co.jatra.noted.repository.UserRepository
import uk.co.jatra.noted.ui.event.EventViewState
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class AddOccurrenceViewModel
    @Inject constructor(
        private val occurrenceRepository: OccurrenceRepository,
        private val eventRepository: EventRepository,
        private val userRepository: UserRepository,
        @Named("Main") val mainThreadScheduler: Scheduler
    ): ViewModel() {

    private var users: List<User>? = emptyList()
    val addOccurrenceViewState: MutableLiveData<EventViewState> = MutableLiveData()
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
            eventRepository.getAllEvents()
                .observeOn(mainThreadScheduler)
                .subscribe({ value ->
                    addOccurrenceViewState.setValue(EventViewState(value))
                }, {
                    //viewstate should have something for errors
                    addOccurrenceViewState.value = EventViewState(emptyList())
                }
                )
        )
    }

    fun addOccurrence(event: Event, userId: String) {
        subscriptions.add(
            occurrenceRepository.addOccurrence(
                Occurrence(
                    userId = userId,
                    time = Date().toString(),
                    eventId = event.id.toString(),
                    detail = event.name + " " + event.description
                )
            )
                .subscribe({
                    Log.d("AOVM", "Success")
                    done()
                }, {
                    Log.e("AOVM", "Error", it)
                })
        )
    }

    private fun done() {
        done.postValue(OneShot())
    }
}

class OneShot {
    private var handled = false

    fun unHandled() = !isHandled()

    private fun isHandled(): Boolean {
        val previous = handled
        handled = true
        return previous
    }
}