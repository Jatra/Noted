package uk.co.jatra.noted.network

import com.google.gson.annotations.SerializedName

data class Occurrence(
    @SerializedName("id") val id: String,
    @SerializedName("time") val time: String,
    @SerializedName("user") val user: String,
    @SerializedName("what") val what: String,
    @SerializedName("detail") val detail: String
)
