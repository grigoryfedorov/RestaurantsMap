package org.grigoryfedorov.restaurantsmap.data.product

import org.grigoryfedorov.restaurantsmap.data.product.model.ApiLocation
import org.grigoryfedorov.restaurantsmap.data.product.model.ApiVenue
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue

class VenuesDataSource(private val venuesService: VenuesService) {
    suspend fun search(location: Location): List<Venue> {

        val ll = "${location.lat},${location.lon}"

        val apiVenuesResponse = venuesService.search(ll).response

        val apiVenues: List<ApiVenue> = apiVenuesResponse?.venues ?: emptyList()

        return apiVenues.mapNotNull {
            mapVenue(it)
        }
    }

    private fun mapVenue(apiVenue: ApiVenue): Venue? {
        return Venue(
            id = apiVenue.id ?: return null,
            name = apiVenue.name ?: return null,
            location = mapLocation(apiVenue.location) ?: return null
        )
    }

    private fun mapLocation(apiLocation: ApiLocation?): Location? {
        return Location(
            lat = apiLocation?.lat ?: return null,
            lon = apiLocation.lon ?: return null
        )
    }

}
