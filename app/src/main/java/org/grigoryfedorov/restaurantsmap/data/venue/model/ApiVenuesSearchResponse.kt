package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiVenuesSearchResponse(
    @SerializedName("venues")
    val venues: List<ApiVenue>?
)
