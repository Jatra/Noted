package uk.co.jatra.noted.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.network.Occurrence
import javax.inject.Inject
import javax.inject.Named

//INITIAL Just fetch one set of data from api.
class OccurrenceRepository @Inject constructor(private val api: Api, @Named("IOScheduler") val ioScheduler: Scheduler) {
    //TO_DO add caching
    fun getData(query: String): Single<List<Occurrence>> {
        return api.getOccurences(query)
            .subscribeOn(ioScheduler)
    }
}