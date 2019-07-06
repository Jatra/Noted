package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

/**
 * A container for the parameters required to create a new [Event]
 *
 * @property[name] the name for the event
 * @property[description] the description for the event
 */
data class EventRequest(
    @SerializedName("event_name") val name: String,
    @SerializedName("event_description") val description: String
)