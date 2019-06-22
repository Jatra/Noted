package uk.co.jatra.noted.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.utils.TimeHelper
import javax.inject.Inject
import javax.inject.Named

//TODO Use a better time apis
private const val ONE_MINUTE = 1000 * 60
const val cacheTimeToLive = ONE_MINUTE

private const val TAG = "OccurrenceRepository"

//INITIAL Just fetch one set of data from api.
class OccurrenceRepository @Inject constructor(
    private val api: Api, @Named("IOScheduler") val ioScheduler: Scheduler,
    private val timeHelper: TimeHelper
) {
    private var cachedData: List<Occurrence> = listOf()
    private var lastRefresh: Long = Long.MIN_VALUE

    fun getData(query: String): Single<List<Occurrence>> {
        return if (cacheValid()) {
            println("cache valid - use cached value")
            Single.just(cachedData)
        } else {
            println("cache invalid - make api call")
            api.getOccurences(query)
                .subscribeOn(ioScheduler)
                .doOnSuccess {
                    cacheData(it)
                }
        }
    }

    private fun cacheData(data: List<Occurrence>) {
        cachedData = data
        lastRefresh = timeHelper.now
    }
    
    private fun cacheValid(): Boolean {
        return if (cachedData.isEmpty()) {
            false
        } else {
            lastRefresh < cacheTimeToLive - timeHelper.now
        }
    }

    fun invalidateCache() {
        lastRefresh = -1
    }

}