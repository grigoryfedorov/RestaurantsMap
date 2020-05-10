package org.grigoryfedorov.restaurantsmap.data.product.model

import com.google.gson.annotations.SerializedName

class ApiVenue(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("location")
    val location: ApiLocation?
)