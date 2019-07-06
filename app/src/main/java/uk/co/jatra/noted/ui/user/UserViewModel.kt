package uk.co.jatra.noted.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.network.User
import uk.co.jatra.noted.network.UserRequest
import uk.co.jatra.noted.repository.Repository

class UserViewModel(
    private val userRepository: Repository<UserRequest, User>,
    private val mainScheduler: Scheduler
) : ViewModel() {
    val userViewState: MutableLiveData<UserViewState> = MutableLiveData()
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
            userRepository.getData()
                .observeOn(mainScheduler)
                .subscribe({ value ->
                    userViewState.setValue(UserViewState(value))
                }, {
                    //viewstate should have something for errors
                    userViewState.setValue(UserViewState(emptyList()))
                }
                )
        )
    }
}
