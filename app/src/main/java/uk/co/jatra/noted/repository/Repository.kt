package uk.co.jatra.noted.repository

import io.reactivex.Scheduler
import io.reactivex.Single
import uk.co.jatra.noted.utils.TimeHelper
import javax.inject.Inject
import javax.inject.Named

private const val TEN_SECONDS = 1000 * 10
const val cacheTimeToLive = TEN_SECONDS

/**
 * A generic Repository.
 *
 * Type parameter R defines the request to add new items.
 * Type parameter T defines the types that the repository will hold.
 * @param[ioScheduler] the [Scheduler] for data operations
 * @param[timeHelper] the [TimeHelper] for cache timing
 * @param[fetcher] the function to fetch new data
 * @param[addFunction] the function to add new instances
 */
class Repository<R, T>  @Inject constructor(
    @Named("IOScheduler") val ioScheduler: Scheduler,
    private val timeHelper: TimeHelper,
    private val fetcher: () -> Single<List<T>>,
    private val addFunction: (request: R) -> Single<T>
) {
    private var cachedData: List<T> = listOf()
    private var lastRefresh: Long = Long.MIN_VALUE

    /**
     * Get data.
     *
     * Items will be returned from the cache if they are still valid,
     * else the [fetcher] function will be invoked to retrieve all items and cache them.
     * @return a [Single] of a list of all items
     */
    fun getData(): Single<List<T>> {
        return if (cacheValid()) {
            println("cache valid - use cached value")
            Single.just(cachedData)
        } else {
            println("cache invalid - make api call")
            fetcher()
                .subscribeOn(ioScheduler)
                .doOnSuccess {
                    cacheData(it)
                }
        }
    }

    /**
     * Add a single item.
     *
     * The item will be added to the server and returned, and added to the cache.
     * @param[request] an instance of the appropriate request.
     * @return a [Single] of the newly created item
     */
    fun addItem(request: R): Single<T> {
        return addFunction(request)
            .subscribeOn(ioScheduler)
            .doOnSuccess { newOccurrence ->
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