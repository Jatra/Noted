package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("id") val id: String,
    @SerializedName("event_name") val eventName: String,
    @SerializedName("event_description") val eventDescription: String
)
