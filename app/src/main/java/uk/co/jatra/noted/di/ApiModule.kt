package uk.co.jatra.noted.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.network.Occurrence
import uk.co.jatra.noted.network.OccurrenceRequest
import uk.co.jatra.noted.network.RestAdapter
import uk.co.jatra.noted.repository.Repository
import uk.co.jatra.noted.utils.TimeHelper
import javax.inject.Inject
import javax.inject.Named

@Module
class ApiModule @Inject constructor(val baseUrl: String) {
    @Provides
    @Named("BaseUrl")
    fun providesBaseUrl(): String = baseUrl

    @Provides
    fun providesApi(adapter: RestAdapter): Api {
        return adapter.api
    }

    @Provides
    fun providesOccurrenceRepository(
        api: Api, @Named("IOScheduler") ioScheduler: Scheduler,
        timeHelper: TimeHelper
    ): Repository<OccurrenceRequest, Occurrence> {
        return Repository(
            ioScheduler,
            timeHelper,
            api::getOccurences,
            api::addOccurrence
        )
    }
}