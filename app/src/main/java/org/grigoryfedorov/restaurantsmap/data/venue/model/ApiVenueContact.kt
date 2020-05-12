package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiVenueContact(
    @SerializedName("phone")
    val phone: String?,

    @SerializedName("formattedPhone")
    val formattedPhone: String?
)
