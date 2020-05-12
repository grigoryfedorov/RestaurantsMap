package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiVenueDetailsResponse(
    @SerializedName("venue")
    val venueDetails: ApiVenueDetails
)
