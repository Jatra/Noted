package uk.co.jatra.noted.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import uk.co.jatra.noted.model.Event
import uk.co.jatra.noted.persistence.EventDao

class EventRepository(private val eventDao: EventDao, val ioScheduler: Scheduler) {
    fun getAllEvents(): Flowable<List<Event>> {
        return eventDao
            .getAllEvents()
            .subscribeOn(ioScheduler)
    }

    fun addEvent(event: Event): Completable {
        return eventDao
            .insertEvent(event)
            .subscribeOn(ioScheduler)
    }
}