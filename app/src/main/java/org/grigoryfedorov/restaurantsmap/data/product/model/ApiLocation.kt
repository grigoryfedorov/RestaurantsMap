package org.grigoryfedorov.restaurantsmap.data.product.model

import com.google.gson.annotations.SerializedName

data class ApiLocation(

    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lng")
    val lon: Double?
)
