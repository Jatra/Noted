package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

/**
 * An Occurrence.
 *
 * A description of something that has happened.
 * Each [Occurrence] in an instance of an [Event]
 * @property[id] The server assigned id of the occurrence.
 * @property[time] The server assigned time of the occurrence
 * @property[user] The user that created the occurrence
 * @property[what] the name of the event the occurrence refers to
 * @property[detail] the description of the event the occurrence refers to
 */
data class Occurrence(
    @SerializedName("id") val id: String,
    @SerializedName("time") val time: String,
    @SerializedName("user") val user: String,
    @SerializedName("what") val what: String,
    @SerializedName("detail") val detail: String
)
