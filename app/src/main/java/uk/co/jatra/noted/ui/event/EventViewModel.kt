package uk.co.jatra.noted.ui.event

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.model.Event
import uk.co.jatra.noted.repository.EventRepository
import javax.inject.Inject
import javax.inject.Named

class EventViewModel(
    private val eventRepository: EventRepository,
    private val mainScheduler: Scheduler
) : ViewModel() {
    val eventViewState: MutableLiveData<EventViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    //INITIAL. Always makes one request at start, no arguments.
    //TODO add some querying.
    init {
        addEvent(Event(1, "Ibruprofen", "200mg"))
        addEvent(Event(2, "Paracetamol", "200mg"))
        addEvent(Event(3, "Decongestant", "1 tablet"))
        addEvent(Event(4, "Aspirin", "2 tabs"))
        getData()
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun addEvent(event: Event) {
        subscriptions.add(
            eventRepository.addEvent(event)
                .observeOn(mainScheduler)
                .subscribe {
                    Log.d("EVM", "event $event added")
                })
    }

    fun getData() {
        subscriptions.add(
            eventRepository.getAllEvents()
                .observeOn(mainScheduler)
                .subscribe({ value ->
                    eventViewState.setValue(EventViewState(value))
                }, {
                    //viewstate should have something for errors
                    eventViewState.setValue(EventViewState(emptyList()))
                }
                )
        )
    }
}

class EventViewModelFactory
@Inject constructor(
    private val repository: EventRepository,
    @Named("Main") private val mainThreadScheduler: Scheduler
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EventViewModel(repository, mainThreadScheduler) as T
}