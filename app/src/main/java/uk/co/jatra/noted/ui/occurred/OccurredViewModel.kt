package uk.co.jatra.noted.ui.occurred

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import uk.co.jatra.noted.repository.OccurenceRepository

class OccurredViewModel : ViewModel() {
    val occuredViewState: MutableLiveData<OccurredViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    //INITIAL. Always makes one request at start, no arguments.
    //TODO add some querying.
    init {
        subscriptions.add(OccurenceRepository.getData("")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { value ->
                occuredViewState.setValue(OccurredViewState(value))
            })
    }
}
