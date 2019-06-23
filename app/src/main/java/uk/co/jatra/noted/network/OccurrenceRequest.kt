package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

data class OccurrenceRequest(
    @SerializedName("userId") val userId: String,
    @SerializedName("eventId") val eventId: String
)