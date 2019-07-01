package uk.co.jatra.noted.ui.occurrence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Scheduler
import uk.co.jatra.noted.network.Event
import uk.co.jatra.noted.network.EventRequest
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.repository.Repository
import javax.inject.Inject
import javax.inject.Named

class AddOccurrenceViewModelFactory @Inject constructor(
    private val eventRepository: Repository<EventRequest, Event>,
    private val occurrenceRepository: Repository<OccurrenceRequest, Occurrence>,
    @Named("Main") val mainThreadScheduler: Scheduler

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddOccurrenceViewModel(occurrenceRepository, eventRepository, mainThreadScheduler) as T
    }
}