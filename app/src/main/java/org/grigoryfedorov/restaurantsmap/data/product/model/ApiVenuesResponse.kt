package org.grigoryfedorov.restaurantsmap.data.product.model

import com.google.gson.annotations.SerializedName

data class ApiVenuesResponse(
    @SerializedName("venues")
    val venues: List<ApiVenue>?
)