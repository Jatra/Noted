package uk.co.jatra.noted.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import org.junit.Test
import uk.co.jatra.noted.network.Api

class OccurrenceRepositoryTest {
    @Test
    fun `should call api`() {
        val api = mockk<Api>()
        every { api.getOccurences(any()) } returns Single.just(emptyList())
        val repository = OccurrenceRepository(api, ImmediateThinScheduler.INSTANCE)
        repository.getData("foo")
        verify { api.getOccurences("foo") }
    }
}