package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiVenueCategory(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("icon")
    val icon: ApiIcon?,

    @SerializedName("primary")
    val primary: Boolean?
)
