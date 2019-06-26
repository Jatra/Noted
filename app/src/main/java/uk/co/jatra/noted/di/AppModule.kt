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
import uk.co.jatra.noted.ui.event.UserViewModel
import uk.co.jatra.noted.ui.occurred.OccurredViewModel
import uk.co.jatra.noted.utils.TimeHelper
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun providesContext(): Context = app

    @Provides
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

    @Provides
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


    @Provides
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

    @Provides
    fun providesEventViewModelFactory(
        eventRepository: Repository<EventRequest, Event>,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<EventRequest, Event, EventViewModel> {
        return NotedViewModelFactory(
            eventRepository,
            mainThreadScheduler
        ) { repository, scheduler -> EventViewModel(repository, scheduler) }
    }

    @Provides
    fun providesOccurrenceViewModelFactory(
        occurrenceRepository: Repository<OccurrenceRequest, Occurrence>,
        @Named("Main") mainThreadScheduler: Scheduler
    ) : NotedViewModelFactory<OccurrenceRequest, Occurrence, OccurredViewModel> {
        return NotedViewModelFactory(
            occurrenceRepository,
            mainThreadScheduler
        ) { repository, scheduler -> OccurredViewModel(repository, scheduler) }
    }

    @Provides
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