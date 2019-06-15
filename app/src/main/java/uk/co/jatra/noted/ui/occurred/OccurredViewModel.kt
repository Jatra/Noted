package uk.co.jatra.noted.ui.occurred

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.repository.OccurenceRepository
import javax.inject.Inject

class OccurredViewModel(occurrenceRepository: OccurenceRepository) : ViewModel() {
    val occuredViewState: MutableLiveData<OccurredViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    //INITIAL. Always makes one request at start, no arguments.
    //TODO add some querying.
    init {
        subscriptions.add(occurrenceRepository.getData("")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ value ->
                occuredViewState.setValue(OccurredViewState(value))
            }, {
                //viewstate should have something for errors
                occuredViewState.setValue(OccurredViewState(emptyList()))
            }
            ))
    }
}

class OccurrenceViewModelFactory @Inject constructor(private val repository: OccurenceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        OccurredViewModel(repository) as T
}
