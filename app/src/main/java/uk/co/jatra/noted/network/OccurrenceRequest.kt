package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

/**
 * A container for the parameters required to create a new [Occurrence] of an [Event]
 *
 * @property[userId] which user to record the occurrence for
 * @property[eventId] what event should be recorded
 */
data class OccurrenceRequest(
    @SerializedName("userId") val userId: String,
    @SerializedName("eventId") val eventId: String
)