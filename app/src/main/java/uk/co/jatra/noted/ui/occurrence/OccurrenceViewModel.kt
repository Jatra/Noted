package uk.co.jatra.noted.ui.occurrence

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.model.Occurrence
import uk.co.jatra.noted.model.User
import uk.co.jatra.noted.repository.OccurrenceRepository
import uk.co.jatra.noted.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named

class OccurrenceViewModel(
    private val occurrenceRepository: OccurrenceRepository,
    val userRepository: UserRepository,
    private val mainScheduler: Scheduler
) : ViewModel() {
    private var users: List<User>? = emptyList()
    val occuredViewState: MutableLiveData<OccurrenceViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    //INITIAL. Always makes one request at start, no arguments.
    //TODO add some querying.
    init {
        getUsers()
        getData()
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun getData() {
        occurrenceRepository.let {
            subscriptions.add(
                it.getAllOccurrences()
                    .observeOn(mainScheduler)
                    .subscribe({ value ->
                        occuredViewState.setValue(OccurrenceViewState(value))
                    }, {
                        //viewstate should have something for errors
                        occuredViewState.setValue(OccurrenceViewState(emptyList()))
                    }
                    )
            )
        }
    }

    fun getUsers() {
        subscriptions.add(
            userRepository.getAllUsers()
                .observeOn(mainScheduler)
                .subscribe({ value ->
                    users = value
                },
                    {
                        Log.e("AOVM", "Failed to get users", it)
                    })
        )
    }

    fun addOccurrence(occurrence: Occurrence) {
        subscriptions.add(
            occurrenceRepository.addOccurrence(occurrence)
                .observeOn(mainScheduler)
                .subscribe({
                    Log.d("OVN", "Occurrence added: $occurrence")
                }, {
                    Log.e("OVM", "Add occurrence failed", it)
                })
        )
    }
}

class OccurrenceViewModelFactory
@Inject constructor(
    private val repository: OccurrenceRepository,
    @Named("Main") private val mainThreadScheduler: Scheduler,
    val userRepository: UserRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        OccurrenceViewModel(repository, userRepository, mainThreadScheduler) as T
}