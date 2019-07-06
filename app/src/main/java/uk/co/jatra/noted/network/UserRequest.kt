package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

/**
 * A container for the parameters required to create a new [User]
 *
 * @property[name] the name for the user
 * @property[notes] the notes for the user
 */
data class UserRequest(
    @SerializedName("name") val name: String,
    @SerializedName("notes") val notes: String
)