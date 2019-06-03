package uk.co.jatra.noted.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    //INITIAL only one api call.
    //TODO Add endpoints
    @GET("/")
    fun getEvents()

    @GET("occurences")
    fun getOccurences(@Query("query") query: String): Single<List<Occurrence>>
}