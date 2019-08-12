package uk.co.jatra.noted.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.model.User
import uk.co.jatra.noted.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named

class UserViewModel(
    private val userRepository: UserRepository,
    private val mainScheduler: Scheduler
) : ViewModel() {
    val userViewState: MutableLiveData<UserViewState> = MutableLiveData()
    private val disposables = CompositeDisposable()

    //INITIAL. Always makes one request at start, no arguments.
    //TODO add some querying.
    init {
        addUser(User(1, "TJR", "me"))
        addUser(User(2, "Mary", "wife"))
        getData()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getData() {
        userRepository.let {
            disposables.add(
                userRepository.getAllUsers()
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

    fun addUser(user: User) {
        disposables.add(
            userRepository.addUser(user)
                .observeOn(mainScheduler)
                .subscribe {
                    Log.d("UVM", "user $user added")
                })
    }
}

class UserViewModelFactory
@Inject constructor(
    private val repository: UserRepository,
    @Named("Main") private val mainThreadScheduler: Scheduler
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserViewModel(repository, mainThreadScheduler) as T
}