package uk.co.jatra.noted.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import uk.co.jatra.noted.model.Occurrence
import uk.co.jatra.noted.persistence.OccurrenceDao

class OccurrenceRepository(private val occurrenceDao: OccurrenceDao, val ioScheduler: Scheduler) {
    fun getAllOccurrences(): Flowable<List<Occurrence>> {
        return occurrenceDao
            .getAllOccurrences()
            .subscribeOn(ioScheduler)
    }

    fun addOccurrence(occurrence: Occurrence): Completable {
        return occurrenceDao
            .insertOccurrence(occurrence)
            .subscribeOn(ioScheduler)
    }
}