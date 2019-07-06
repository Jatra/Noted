package uk.co.jatra.noted.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Provides the client side Retorfit implementation of the API
 *
 * @property [baseUrl] the baseUrl of the service
 */
@Singleton
class RestAdapter @Inject constructor(@Named("BaseUrl") val baseUrl: String) {

    /**
     * the [Api] implementation
     */
    val api by lazy {
        createApi()
    }

    private fun createApi(): Api {
        val gson = GsonBuilder()
            .create()

        val retrofit = Retrofit.Builder()
            .client(createClient())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(Api::class.java)
    }

    //TODO add authentication, api key injection, error handling, caching
    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
}

