package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiVenuePhoto(
    @SerializedName("prefix")
    val prefix: String?,

    @SerializedName("suffix")
    val suffix: String?
)
