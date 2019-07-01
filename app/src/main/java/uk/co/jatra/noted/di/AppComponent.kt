package uk.co.jatra.noted.di

import dagger.Component
import uk.co.jatra.noted.ui.event.EventFragment
import uk.co.jatra.noted.ui.occurrence.AddOccurrenceBottomSheet
import uk.co.jatra.noted.ui.occurrence.OccurrenceFragment
import uk.co.jatra.noted.ui.user.UserFragment
import javax.inject.Singleton

//Specify which classes need injection, using which module.
@Component(modules = [AppModule::class, ApiModule::class, SchedulerModule::class])
@Singleton
interface AppComponent {
    fun inject(target: EventFragment)
    fun inject(target: UserFragment)
    fun inject(target: OccurrenceFragment)
    fun inject(target: AddOccurrenceBottomSheet)
}
