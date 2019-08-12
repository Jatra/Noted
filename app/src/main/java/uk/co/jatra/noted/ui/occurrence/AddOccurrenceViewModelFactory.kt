package uk.co.jatra.noted.ui.occurrence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Scheduler
import uk.co.jatra.noted.repository.EventRepository
import uk.co.jatra.noted.repository.OccurrenceRepository
import uk.co.jatra.noted.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named

class AddOccurrenceViewModelFactory @Inject constructor(
    private val eventRepository: EventRepository,
    private val occurrenceRepository: OccurrenceRepository,
    val userRepository: UserRepository,
    @Named("Main") val mainThreadScheduler: Scheduler

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddOccurrenceViewModel(occurrenceRepository, eventRepository, userRepository, mainThreadScheduler) as T
    }
}