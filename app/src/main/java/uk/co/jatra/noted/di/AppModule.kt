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

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun providesContext(): Context = app

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