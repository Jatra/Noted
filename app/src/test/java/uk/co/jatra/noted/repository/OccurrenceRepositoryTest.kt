package uk.co.jatra.noted.repository

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import org.junit.Before
import org.junit.Test
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.utils.TimeHelper

private const val BASE_TIME = 1L
private const val WITHIN_CACHE_TIME = BASE_TIME + cacheTimeToLive - 1000
private const val AFTER_CACHE_TIME = BASE_TIME + cacheTimeToLive + 1


class OccurrenceRepositoryTest {
    @RelaxedMockK
    private lateinit var timeHelper: TimeHelper
    @RelaxedMockK
    private lateinit var api: Api
    private lateinit var repository: OccurrenceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { timeHelper.now } returns BASE_TIME
        every { api.getOccurences(any()) } returns Single.just(listOf(mockk()))
        repository = OccurrenceRepository(api, ImmediateThinScheduler.INSTANCE, timeHelper)
//        repository.invalidateCache()
    }

    @Test
    fun `should call api`() {
        repository.getData("foo")
        verify { api.getOccurences("foo") }
    }

    @Test
    fun `should use cache`() {
        repository.getData("foo").subscribe()
        every { timeHelper.now } returns WITHIN_CACHE_TIME
        repository.getData("foo").subscribe()
        verify(atMost = 1) { api.getOccurences("foo") }
    }

    @Test
    fun `should not use cache`() {
        repository.getData("foo").subscribe()
        every { timeHelper.now } returns AFTER_CACHE_TIME
        verify(atMost = 1) { api.getOccurences("foo") }
    }

}