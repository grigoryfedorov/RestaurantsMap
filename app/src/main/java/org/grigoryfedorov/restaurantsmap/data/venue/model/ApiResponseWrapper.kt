package org.grigoryfedorov.restaurantsmap.data.venue.model

import com.google.gson.annotations.SerializedName

data class ApiResponseWrapper<T>(
    @SerializedName("response")
    var response: T?
)
