package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

/**
 * An Event.
 *
 * A description of something that can be recorded multiple times.
 * Each instance of an [Event] is an [Occurrence]
 * @property[id] The server assigned id of the event.
 * @property[eventName] The name of the event.
 * @property[eventDescription] The description of the event.
 */
data class Event(
    @SerializedName("id") val id: String,
    @SerializedName("event_name") val eventName: String,
    @SerializedName("event_description") val eventDescription: String
)
