package uk.co.jatra.noted.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * An Occurrence.
 *
 * A description of something that has happened.
 * Each [Occurrence] in an instance of an [Event]
 * @property[id] The server assigned id of the occurrence.
 * @property[time] The server assigned time of the occurrence
 * @property[userId] The user that created the occurrence
 * @property[eventId] the event the occurrence refers to
 * @property[detail] anything extra
 */
@Entity(tableName = "occurrence")
data class Occurrence(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @SerializedName("id") val id: Long = 0,
    @ColumnInfo(name = "time") @SerializedName("time") val time: String,
    @ColumnInfo(name = "userId") @SerializedName("userId") val userId: String,
    @ColumnInfo(name = "eventId") @SerializedName("eventId") val eventId: String,
    @ColumnInfo(name = "notes") @SerializedName("detail") val detail: String
)
