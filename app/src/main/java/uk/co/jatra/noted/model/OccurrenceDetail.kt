package uk.co.jatra.noted.model

import androidx.room.DatabaseView
import com.google.gson.annotations.SerializedName

@DatabaseView(
    "SELECT occurrence.id as id, " +
            "user.name as userName, " +
            "event.name as eventName, " +
            "event.description as description, " +
            "time, " +
            "occurrence.notes as notes " +
            "FROM user, occurrence, event " +
            "WHERE occurrence.userId = user.id " +
            "AND occurrence.eventId = event.id"
)
data class OccurrenceDetail(
    @SerializedName("id") val id: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("eventName") val eventName: String,
    @SerializedName("description") val description: String,
    @SerializedName("time") val time: String,
    @SerializedName("notes") val notes: String
)