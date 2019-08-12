package uk.co.jatra.noted.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import uk.co.jatra.noted.model.Event
import uk.co.jatra.noted.model.Occurrence
import uk.co.jatra.noted.model.User
import uk.co.jatra.noted.network.EventRequest
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.network.UserRequest
import uk.co.jatra.noted.persistence.EventDao
import uk.co.jatra.noted.persistence.OccurrenceDao
import uk.co.jatra.noted.persistence.UserDao
import uk.co.jatra.noted.repository.EventRepository
import uk.co.jatra.noted.repository.OccurrenceRepository
import uk.co.jatra.noted.repository.Repository
import uk.co.jatra.noted.repository.UserRepository
import uk.co.jatra.noted.ui.NotedViewModelFactory
import uk.co.jatra.noted.ui.event.EventViewModel
import uk.co.jatra.noted.ui.occurrence.OccurrenceViewModel
import uk.co.jatra.noted.ui.user.UserViewModel
import javax.inject.Named
import javax.inject.Singleton

/**
 * AppModule.
 *
 * Provides of classes to satisfy Dagger dependency.
 */
@Module
class AppModule(private val app: Application) {
    /**
     * provide the Application Context
     *
     * @return the Application [Context]
     */
    @Provides
    @Singleton
    fun providesContext(): Context = app

    /**
     * provide the Event Repository
     *
     * @param[ioScheduler] the Rx [Scheduler] on which to make api calls
     * @return A [Repository] for [Event]
     */
    @Provides
    @Singleton
    fun providesEventRepository(
        eventDao: EventDao,
        @Named("IOScheduler") ioScheduler: Scheduler
    ): EventRepository {
        return EventRepository(eventDao, ioScheduler)
    }

    /**
     * provide the Occurrence Repository
     *
     * @param[ioScheduler] the Rx [Scheduler] on which to make api calls
     * @return A [Repository] for [Occurrence]
     */
    @Provides
    @Singleton
    fun providesOccurrenceRepository(
        occurrenceDao: OccurrenceDao,
        @Named("IOScheduler") ioScheduler: Scheduler
    ): OccurrenceRepository {
        return OccurrenceRepository(occurrenceDao, ioScheduler)
    }

    /**
     * provide the User Repository
     *
     * @param[ioScheduler] the Rx [Scheduler] on which to make api calls
     * @return A [Repository] for [User]
     */
    @Provides
    @Singleton
    fun providesUserRepository(
        userDao: UserDao,
        @Named("IOScheduler") ioScheduler: Scheduler
    ) = UserRepository(userDao, ioScheduler)

    /**
     * provide the Factory for generating [EventViewModel]
     *
     * @param[eventRepository] the Event Repository
     * @param[mainThreadScheduler] the Main Thread Rx [Scheduler]
     * @return A [NotedViewModelFactory] that can supply [EventViewModel]
     */
    @Provides
    @Singleton
    fun providesEventViewModelFactory(
        eventRepository: EventRepository,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<EventRequest, Event, EventViewModel> {
        return NotedViewModelFactory(
            null,
            mainThreadScheduler
        ) { _, scheduler -> EventViewModel(eventRepository, scheduler) }
    }

    /**
     * provide the Factory for generating [OccurrenceViewModel]
     *
     * @param[occurrenceRepository] the Occurrence Repository
     * @param[mainThreadScheduler] the Main Thread Rx [Scheduler]
     * @return A [NotedViewModelFactory] that can supply [OccurrenceViewModel]
     */
    @Provides
    @Singleton
    fun providesOccurrenceViewModelFactory(
        occurrenceRepository: OccurrenceRepository,
        userRepository: UserRepository,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<OccurrenceRequest, Occurrence, OccurrenceViewModel> {
        return NotedViewModelFactory(
            null,
            mainThreadScheduler
        ) { _, scheduler -> OccurrenceViewModel(occurrenceRepository, userRepository, scheduler) }
    }

    /**
     * provide the Factory for generating [UserViewModel]
     *
     * @param[userRepository] the User Repository
     * @param[mainThreadScheduler] the Main Thread Rx [Scheduler]
     * @return A [NotedViewModelFactory] that can supply [UserViewModel]
     */
    @Provides
    @Singleton
    fun providesUserViewModelFactory(
        userRepository: UserRepository,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<UserRequest, User, UserViewModel> {
        return NotedViewModelFactory(
            null,
            mainThreadScheduler
        ) { _, scheduler -> UserViewModel(userRepository, scheduler) }
    }
}