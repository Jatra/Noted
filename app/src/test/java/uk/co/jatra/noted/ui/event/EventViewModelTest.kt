package uk.co.jatra.noted.ui.event

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ImmediateThinScheduler
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.jatra.noted.model.Event
import uk.co.jatra.noted.repository.EventRepository

class EventViewModelTest {

    @RelaxedMockK
    lateinit var repository: EventRepository
    private val testScheduler: Scheduler = ImmediateThinScheduler.INSTANCE
    private val event = Event(1, "name", "description")

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should make initial request`() {
        EventViewModel(repository, testScheduler)
        verify { repository.getAllEvents() }
    }

    @Test
    fun `should set view state`() {
        every { repository.getAllEvents() } returns Flowable.just(listOf(event))
        val occurredViewModel = EventViewModel(repository, testScheduler)
        Assertions.assertThat(occurredViewModel.eventViewState.value).isEqualTo(EventViewState(listOf(event)))
    }
}