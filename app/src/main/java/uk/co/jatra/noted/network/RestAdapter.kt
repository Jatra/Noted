package uk.co.jatra.noted.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//INITIAL SETUP: using global variable
//TODO change to injection: allows substitution for testing.
val api by lazy {
    createApi()
}

private fun createApi(): Api {
    val gson = GsonBuilder()
        .create()

    val retrofit = Retrofit.Builder()
        .client(createClient())
        .baseUrl(BASE_URL)
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