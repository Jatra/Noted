package uk.co.jatra.noted.di

import dagger.Module
import dagger.Provides
import uk.co.jatra.noted.network.Api
import uk.co.jatra.noted.network.RestAdapter
import javax.inject.Inject

@Module
class ApiModule @Inject constructor() {
    @Provides
    fun providesApi(adapter: RestAdapter): Api {
        return adapter.api
    }
}