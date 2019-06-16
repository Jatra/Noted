package uk.co.jatra.noted.di

import dagger.Component
import uk.co.jatra.noted.ui.occurred.OccurredFragment
import javax.inject.Singleton

//Specify which classes need injection, using which module.
@Component(modules = [AppModule::class, ApiModule::class, SchedulerModule::class])
@Singleton
interface AppComponent {
    fun inject(target: OccurredFragment)
}
