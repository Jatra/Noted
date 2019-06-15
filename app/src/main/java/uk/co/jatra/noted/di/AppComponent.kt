package uk.co.jatra.noted.di

import dagger.Component
import uk.co.jatra.noted.repository.OccurenceRepository
import uk.co.jatra.noted.ui.occurred.OccurredFragment
import uk.co.jatra.noted.ui.occurred.OccurrenceViewModelFactory

//Specify which classes need injection, using which module.
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(target: OccurredFragment)
    fun inject(target: OccurenceRepository)
    fun inject(target: OccurrenceViewModelFactory)
}
