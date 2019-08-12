package uk.co.jatra.noted.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * A User.
 *
 * The detail of a user that can have instances of [Occurrence].
 * @property[id] The server assigned id of the user.
 * @property[name] The name of the user.
 * @property[notes] Notes regarding the user.
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @SerializedName("id") val id: Long,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "notes") @SerializedName("notes") val notes: String
)
