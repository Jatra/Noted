package uk.co.jatra.noted.di

import dagger.Module
import dagger.Provides
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.network.RestAdapter
import javax.inject.Inject
import javax.inject.Named

/**
 * Dagger module for the Rest Interface.
 */
@Module
class ApiModule @Inject constructor(val baseUrl: String) {
    /**
     * Provision of the base URL for the backend service.
     * @return baseUrl for the rest adapter.
     */
    @Provides
    @Named("BaseUrl")
    fun providesBaseUrl(): String = baseUrl

    /**
     * Provides the interface to the server API.
     * @param[adapter] the Retrofit Adapter
     * @return the local api implementation
     */
    @Provides
    fun providesApi(adapter: RestAdapter): Api {
        return adapter.api
    }
}