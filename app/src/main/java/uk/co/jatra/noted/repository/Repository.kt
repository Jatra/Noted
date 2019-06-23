package uk.co.jatra.noted.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import uk.co.jatra.noted.utils.TimeHelper
import javax.inject.Inject
import javax.inject.Named

private const val TEN_SECONDS = 1000 * 10
const val cacheTimeToLive = TEN_SECONDS

class Repository<R, T>  @Inject constructor(
    @Named("IOScheduler") val ioScheduler: Scheduler,
    private val timeHelper: TimeHelper,
    private val fetcher: (query: String) -> Single<List<T>>,
    private val addFunction: (request: R) -> Single<T>
) {
    private var cachedData: List<T> = listOf()
    private var lastRefresh: Long = Long.MIN_VALUE

    fun getData(query: String): Single<List<T>> {
        return if (cacheValid()) {
            println("cache valid - use cached value")
            Single.just(cachedData)
        } else {
            println("cache invalid - make api call")
            fetcher(query)
                .subscribeOn(ioScheduler)
                .doOnSuccess {
                    cacheData(it)
                }
        }
    }

    fun addItem(request: R): Single<T> {
        return addFunction(request)
            .subscribeOn(ioScheduler)
            .doOnSuccess {newOccurrence ->
                if (cacheValid()) {
                    cachedData = cachedData + newOccurrence
                }
            }
    }

    private fun cacheData(data: List<T>) {
        cachedData = data
        lastRefresh = timeHelper.now
    }

    private fun cacheValid(): Boolean {
        val now = timeHelper.now
        return if (cachedData.isEmpty()) {
            false
        } else {
            now - lastRefresh < cacheTimeToLive
        }
    }

    fun invalidateCache() {
        lastRefresh = -1
    }

}