package uk.co.jatra.noted.ui.occurrence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.jatra.noted.model.Occurrence
import uk.co.jatra.noted.repository.OccurrenceRepository
import uk.co.jatra.noted.repository.UserRepository

class OccurrenceViewModelTest {

    @RelaxedMockK
    lateinit var occurrenceRepository: OccurrenceRepository
    @RelaxedMockK
    lateinit var userRepository: UserRepository
    private val testScheduler: Scheduler = ImmediateThinScheduler.INSTANCE
    private val occurrence = Occurrence(1, "time", "user", "what", "detail")

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should make initial request`() {
        OccurrenceViewModel(occurrenceRepository, userRepository, testScheduler)
        verify { occurrenceRepository.getAllOccurrences() }
    }

    @Test
    fun `should set view state`() {
        every { occurrenceRepository.getAllOccurrences() } returns Flowable.just(listOf(occurrence))
        val occurredViewModel = OccurrenceViewModel(occurrenceRepository, userRepository, testScheduler)
        assertThat(occurredViewModel.occuredViewState.value).isEqualTo(OccurrenceViewState(listOf(occurrence)))
    }
}