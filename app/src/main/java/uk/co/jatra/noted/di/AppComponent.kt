package uk.co.jatra.noted.di

import dagger.Component
import uk.co.jatra.noted.ui.event.EventFragment
import uk.co.jatra.noted.ui.occurrence.AddOccurrenceBottomSheet
import uk.co.jatra.noted.ui.occurrence.OccurrenceFragment
import uk.co.jatra.noted.ui.user.UserFragment
import javax.inject.Singleton

/**
 * Dagger app component defining the injections possible
 */
@Component(modules = [AppModule::class, ApiModule::class, SchedulerModule::class])
@Singleton
interface AppComponent {
    /**
     * Inject into the EventFragment
     * param[target] The [EventFragment]
     */
    fun inject(target: EventFragment)

    /**
     * Inject into the UserFragment
     * param[target] The [UserFragment]
     */
    fun inject(target: UserFragment)

    /**
     * Inject into the OccurrenceFragment
     * param[target] The [OccurrenceFragment]
     */
    fun inject(target: OccurrenceFragment)

    /**
     * Inject into the AddOccurrenceBottomSheet
     * param[target] The [AddOccurrenceBottomSheet]
     */
    fun inject(target: AddOccurrenceBottomSheet)
}
