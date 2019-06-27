package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("name") val name: String,
    @SerializedName("notes") val notes: String
)