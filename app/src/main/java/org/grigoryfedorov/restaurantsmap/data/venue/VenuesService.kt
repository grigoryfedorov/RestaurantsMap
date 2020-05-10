package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiResponseWrapper
import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiVenueDetailsResponse
import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiVenuesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenuesService {
    @GET("v2/venues/search")
    suspend fun search(
        @Query("ll") ll: String,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Query("categoryId") categoryId: String
    ): ApiResponseWrapper<ApiVenuesSearchResponse>

    @GET("/v2/venues/{id}")
    suspend fun getDetails(
        @Path("id") id: String
    ): ApiResponseWrapper<ApiVenueDetailsResponse>
}
