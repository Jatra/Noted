package uk.co.jatra.noted.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Scheduler
import uk.co.jatra.noted.repository.Repository
import javax.inject.Inject
import javax.inject.Named

class NotedViewModelFactory<R, T, V: ViewModel>
@Inject constructor(
    private val repository: Repository<R, T>?,
    @Named("Main") private val mainThreadScheduler: Scheduler,
    private val viewModelCreator: (Repository<R, T>?, Scheduler) -> V
) : ViewModelProvider.Factory {
    override fun <V : ViewModel?> create(viewModelClass: Class<V>): V {
        return viewModelCreator(repository, mainThreadScheduler) as V
    }
}
