package uk.co.jatra.noted.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @GET("occurences")
    fun getOccurences(@Query("query") query: String): Single<List<Occurrence>>

    @GET("users")
    fun getUsers(@Query("query") query: String): Single<List<User>>

    @GET("events")
    fun getEvents(@Query("query") query: String): Single<List<Event>>

    @POST("occurence")
    fun addOccurrence(@Body request: OccurrenceRequest) : Single<Occurrence>
}