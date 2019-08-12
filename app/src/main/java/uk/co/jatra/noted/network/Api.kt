package uk.co.jatra.noted.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uk.co.jatra.noted.model.Event

/**
 * Definition of the Server API for Retrofit generation.
 */
interface Api {

    /**
     * Get all occurrences.
     *
     * @return an RxJava [Single] of a the list of [Occurrence]
     */
    @GET("occurences")
    fun getOccurences(): Single<List<Occurrence>>

    /**
     * Get all users.
     *
     * @return an RxJava [Single] of a the list of [User]
     */
    @GET("users")
    fun getUsers(): Single<List<User>>

    /**
     * Get all events.
     *
     * @return an RxJava [Single] of a the list of [Event]
     */
    @GET("events")
    fun getEvents(): Single<List<Event>>

    /**
     * Add a single [Occurrence]
     *
     * @param[request] an [OccurrenceRequest]
     * @return the newly created [Occurrence]
     */
    @POST("occurence")
    fun addOccurrence(@Body request: OccurrenceRequest) : Single<Occurrence>

    /**
     * Add a single [Event]
     *
     * @param[request] an [EventRequest]
     * @return the newly created [Event]
     */
    @POST("event")
    fun addEvent(@Body request: EventRequest) : Single<Event>

    /**
     * Add a single [User]
     *
     * @param[request] an [UserRequest]
     * @return the newly created [User]
     */
    @POST("user")
    fun addUser(@Body request: UserRequest) : Single<User>
}