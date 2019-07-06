package uk.co.jatra.noted.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import uk.co.jatra.noted.network.*
import uk.co.jatra.noted.repository.Repository
import uk.co.jatra.noted.ui.NotedViewModelFactory
import uk.co.jatra.noted.ui.event.EventViewModel
import uk.co.jatra.noted.ui.occurrence.OccurrenceViewModel
import uk.co.jatra.noted.ui.user.UserViewModel
import uk.co.jatra.noted.utils.TimeHelper
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
     * provide the Occurrence Repository
     *
     * @param[api] a instance of the client side of the [Api]
     * @param[ioScheduler] the Rx [Scheduler] on which to make api calls
     * @param[timeHelper] an instance of [TimeHelper] for cache timing
     * @return A [Repository] for [Occurrence]
     */
    @Provides
    @Singleton
    fun providesOccurrenceRepository(
        api: Api, @Named("IOScheduler") ioScheduler: Scheduler,
        timeHelper: TimeHelper
    ): Repository<OccurrenceRequest, Occurrence> {
        return Repository(
            ioScheduler,
            timeHelper,
            api::getOccurences,
            api::addOccurrence
        )
    }

    /**
     * provide the Event Repository
     *
     * @param[api] a instance of the client side of the [Api]
     * @param[ioScheduler] the Rx [Scheduler] on which to make api calls
     * @param[timeHelper] an instance of [TimeHelper] for cache timing
     * @return A [Repository] for [Event]
     */
    @Provides
    @Singleton
    fun providesEventRepository(
        api: Api, @Named("IOScheduler") ioScheduler: Scheduler,
        timeHelper: TimeHelper
    ): Repository<EventRequest, Event> {
        return Repository(
            ioScheduler,
            timeHelper,
            api::getEvents,
            api::addEvent
        )
    }

    /**
     * provide the User Repository
     *
     * @param[api] a instance of the client side of the [Api]
     * @param[ioScheduler] the Rx [Scheduler] on which to make api calls
     * @param[timeHelper] an instance of [TimeHelper] for cache timing
     * @return A [Repository] for [User]
     */
    @Provides
    @Singleton
    fun providesUserRepository(
        api: Api, @Named("IOScheduler") ioScheduler: Scheduler,
        timeHelper: TimeHelper
    ): Repository<UserRequest, User> {
        return Repository(
            ioScheduler,
            timeHelper,
            api::getUsers,
            api::addUser
        )
    }

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
        eventRepository: Repository<EventRequest, Event>,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<EventRequest, Event, EventViewModel> {
        return NotedViewModelFactory(
            eventRepository,
            mainThreadScheduler
        ) { repository, scheduler -> EventViewModel(repository, scheduler) }
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
        occurrenceRepository: Repository<OccurrenceRequest, Occurrence>,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<OccurrenceRequest, Occurrence, OccurrenceViewModel> {
        return NotedViewModelFactory(
            occurrenceRepository,
            mainThreadScheduler
        ) { repository, scheduler -> OccurrenceViewModel(repository, scheduler) }
    }

    /**
     * provide the Factory for generating [UserViewModel]
     *
     * @param[userRepository] the Occurrence Repository
     * @param[mainThreadScheduler] the Main Thread Rx [Scheduler]
     * @return A [NotedViewModelFactory] that can supply [UserViewModel]
     */
    @Provides
    @Singleton
    fun providesUserViewModelFactory(
        userRepository: Repository<UserRequest, User>,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<UserRequest, User, UserViewModel> {
        return NotedViewModelFactory(
            userRepository,
            mainThreadScheduler
        ) { repository, scheduler -> UserViewModel(repository, scheduler) }
    }
}