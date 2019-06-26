package uk.co.jatra.noted.ui.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.network.User
import uk.co.jatra.noted.network.UserRequest
import uk.co.jatra.noted.repository.Repository

class UserViewModel(
    private val eventRepository: Repository<UserRequest, User>,
    private val mainScheduler: Scheduler
) : ViewModel() {
    val eventViewState: MutableLiveData<UserViewState> = MutableLiveData()
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
            eventRepository.getData("")
                .observeOn(mainScheduler)
                .subscribe({ value ->
                    eventViewState.setValue(UserViewState(value))
                }, {
                    //viewstate should have something for errors
                    eventViewState.setValue(UserViewState(emptyList()))
                }
                )
        )
    }
}
