package uk.co.jatra.noted.di

import dagger.Component
import uk.co.jatra.noted.ui.event.EventFragment
import uk.co.jatra.noted.ui.event.UserFragment
import uk.co.jatra.noted.ui.occurred.OccurredFragment
import javax.inject.Singleton

//Specify which classes need injection, using which module.
@Component(modules = [AppModule::class, ApiModule::class, SchedulerModule::class])
@Singleton
interface AppComponent {
    fun inject(target: EventFragment)
    fun inject(target: UserFragment)
    fun inject(target: OccurredFragment)
}
