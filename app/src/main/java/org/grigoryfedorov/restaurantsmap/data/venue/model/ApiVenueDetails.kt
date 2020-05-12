package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiVenueDetails(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("location")
    val location: ApiLocation?,

    @SerializedName("categories")
    val categories: List<ApiVenueCategory>?,

    @SerializedName("rating")
    val rating: Double?,

    @SerializedName("ratingColor")
    val ratingColor: String?,

    @SerializedName("hours")
    val hours: ApiVenueHours?,

    @SerializedName("bestPhoto")
    val bestPhoto: ApiVenuePhoto?
)
