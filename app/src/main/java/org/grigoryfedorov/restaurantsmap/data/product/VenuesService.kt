package org.grigoryfedorov.restaurantsmap.data.product

import org.grigoryfedorov.restaurantsmap.data.product.model.ApiResponseWrapper
import org.grigoryfedorov.restaurantsmap.data.product.model.ApiVenuesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VenuesService {
    @GET("v2/venues/search")
    suspend fun search(@Query("ll") ll: String): ApiResponseWrapper<ApiVenuesResponse>
}
