package org.grigoryfedorov.restaurantsmap.data.product.model

import com.google.gson.annotations.SerializedName

data class ApiResponseWrapper<T>(
    @SerializedName("response")
    var response: T?
)
