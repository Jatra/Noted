package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

data class EventRequest(
    @SerializedName("event_name") val name: String,
    @SerializedName("event_description") val description: String
)