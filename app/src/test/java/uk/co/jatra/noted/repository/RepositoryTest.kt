package uk.co.jatra.noted.repository

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single.just
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.utils.TimeHelper

private const val BASE_TIME = 1L
private const val WITHIN_CACHE_TIME = BASE_TIME + cacheTimeToLive - 1000
private const val AFTER_CACHE_TIME = BASE_TIME + cacheTimeToLive + 1
private const val EVENT_ID = "eventId"
private const val USER_ID = "userId"

class RepositoryTest {
    @RelaxedMockK
    private lateinit var timeHelper: TimeHelper
    @RelaxedMockK
    private lateinit var api: Api
    private lateinit var occurrenceRepository: Repository<OccurrenceRequest, Occurrence>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { timeHelper.now } returns BASE_TIME
        every { api.getOccurences(any()) } returns just(listOf(mockk()))
        every { api.addOccurrence(any()) } returns just(mockk())
        occurrenceRepository = Repository(ImmediateThinScheduler.INSTANCE, timeHelper, api::getOccurences, api::addOccurrence)
    }

    @Test
    fun `should call api for get`() {
        occurrenceRepository.getData("foo")
        verify { api.getOccurences("foo") }
    }

    @Test
    fun `should call api for add`() {
        val request = OccurrenceRequest(USER_ID, EVENT_ID)
        occurrenceRepository.addItem(request)
        verify { api.addOccurrence(request) }
    }

    @Test
    fun `should add to cache`() {
        val request = OccurrenceRequest(USER_ID, EVENT_ID)
        var newOccurence: Occurrence? = null
        var returnedData: List<Occurrence> = emptyList()
        occurrenceRepository.getData("").subscribe()
        occurrenceRepository.addItem(request).subscribe{ newValue -> newOccurence = newValue }
        occurrenceRepository.getData("").subscribe { value -> returnedData = value }
        assertThat(returnedData).contains(newOccurence)
    }

    @Test
    fun `should use cache`() {
        occurrenceRepository.getData("foo").subscribe()
        every { timeHelper.now } returns WITHIN_CACHE_TIME
        occurrenceRepository.getData("foo").subscribe()
        verify(atMost = 1) { api.getOccurences("foo") }
    }

    @Test
    fun `should not use cache`() {
        occurrenceRepository.getData("foo").subscribe()
        every { timeHelper.now } returns AFTER_CACHE_TIME
        verify(atMost = 1) { api.getOccurences("foo") }
    }
}