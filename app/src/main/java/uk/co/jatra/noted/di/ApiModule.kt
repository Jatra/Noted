package uk.co.jatra.noted.di

import dagger.Module
import dagger.Provides
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.network.BASE_URL
import uk.co.jatra.noted.network.RestAdapter
import javax.inject.Inject
import javax.inject.Named

@Module
class ApiModule @Inject constructor() {
    @Provides
    @Named("BaseUrl")
    fun providesBaseUrl(): String = BASE_URL

    @Provides
    fun providesApi(adapter: RestAdapter): Api {
        return adapter.api
    }
}