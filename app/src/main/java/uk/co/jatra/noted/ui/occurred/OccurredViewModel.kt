package uk.co.jatra.noted.ui.occurred

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.repository.Repository

class OccurredViewModel(
    private val occurrenceRepository: Repository<OccurrenceRequest, Occurrence>,
    private val mainScheduler: Scheduler
) : ViewModel() {
    val occuredViewState: MutableLiveData<OccurredViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

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
            occurrenceRepository.getData("")
                .observeOn(mainScheduler)
                .subscribe({ value ->
                    occuredViewState.setValue(OccurredViewState(value))
                }, {
                    //viewstate should have something for errors
                    occuredViewState.setValue(OccurredViewState(emptyList()))
                }
                )
        )
    }
}
