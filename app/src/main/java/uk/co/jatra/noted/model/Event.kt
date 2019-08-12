package uk.co.jatra.noted.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * An Event.
 *
 * A description of something that can be recorded multiple times.
 * Each instance of an [Event] is an [Occurrence]
 * @property[id] The server assigned id of the event.
 * @property[name] The name of the event.
 * @property[description] The description of the event.
 */
@Entity(tableName = "event")
data class Event(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @SerializedName("id") val id: Long,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "description") @SerializedName("description") val description: String
)
