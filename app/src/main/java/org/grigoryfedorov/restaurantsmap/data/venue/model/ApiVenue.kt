package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

class ApiVenue(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("location")
    val location: ApiLocation?,

    @SerializedName("categories")
    val categories: List<ApiVenueCategory>?
)