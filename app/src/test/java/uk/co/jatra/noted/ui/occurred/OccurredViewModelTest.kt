package uk.co.jatra.noted.ui.occurred

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.repository.Repository

class OccurredViewModelTest {

    @RelaxedMockK
    lateinit var repository: Repository<OccurrenceRequest, Occurrence>
    private val testScheduler: Scheduler = ImmediateThinScheduler.INSTANCE
    private val occurrence = Occurrence("id", "time", "user", "what", "detail")

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should make initial request`() {
        OccurredViewModel(repository, testScheduler)
        verify { repository.getData("") }
    }

    @Test
    fun `should set view state`() {
        every { repository.getData("") } returns Single.just(listOf(occurrence))
        val occurredViewModel = OccurredViewModel(repository, testScheduler)
        assertThat(occurredViewModel.occuredViewState.value).isEqualTo(OccurredViewState(listOf(occurrence)))
    }
}