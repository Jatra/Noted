package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

/**
 * A User.
 *
 * The detail of a user that can have instances of [Occurrence].
 * @property[id] The server assigned id of the user.
 * @property[name] The name of the user.
 * @property[notes] Notes regarding the user.
 */
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("notes") val notes: String
)
