package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiVenueHours(

    @SerializedName("status")
    val status: String?
)
