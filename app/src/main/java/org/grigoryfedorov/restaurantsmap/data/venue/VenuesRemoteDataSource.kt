package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiVenue
import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiVenueCategory
import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiVenueDetails
import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiVenuePhoto
import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiVenueHours
import org.grigoryfedorov.restaurantsmap.data.venue.model.ApiLocation
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails
import org.grigoryfedorov.restaurantsmap.domain.VenueLocation
import java.io.IOException

class VenuesRemoteDataSource(private val venuesService: VenuesService) {

    companion object {
        const val CATEGORY_FOOD = "4d4b7105d754a06374d81259"
        const val INTENT_BROWSE = "browse"
        const val MAX_LIMIT = 50
    }

    suspend fun search(
        locationBox: LocationBox,
        category: VenueCategory
    ): List<Venue> {

        val apiVenuesResponse = venuesService.search(
            northEast = mapLocation(locationBox.northEast),
            southWest = mapLocation(locationBox.southWest),
            intent = INTENT_BROWSE,
            limit = MAX_LIMIT,
            categoryId = mapCategory(category)
        ).response

        val apiVenues: List<ApiVenue> = apiVenuesResponse?.venues ?: emptyList()

        return apiVenues.mapNotNull {
            mapVenue(it)
        }
    }

    suspend fun getDetails(id: String): VenueDetails {
        val venueDetails = venuesService.getDetails(id).response?.venueDetails
        return mapVenueDetails(venueDetails) ?: throw IOException("Parse error")
    }

    private fun mapLocation(location: Location): String {
        return "${location.lat},${location.lon}"
    }

    private fun mapVenueDetails(venueDetails: ApiVenueDetails?): VenueDetails? {
        return VenueDetails(
            Venue(
                id = venueDetails?.id ?: return null,
                name = venueDetails.name ?: return null,
                venueLocation = mapLocation(venueDetails.location) ?: return null,
                category = mapCategory(venueDetails.categories)
            ),
            rating = venueDetails.rating,
            hoursStatus = mapHours(venueDetails.hours),
            bestPhoto = mapBestPhoto(venueDetails.bestPhoto)
        )
    }

    private fun mapBestPhoto(bestPhoto: ApiVenuePhoto?): String? {
        if (bestPhoto?.prefix == null || bestPhoto.suffix == null) {
            return null
        }
        return "${bestPhoto.prefix}original${bestPhoto.suffix}"
    }

    private fun mapHours(hours: ApiVenueHours?): String? {
        return hours?.status
    }

    private fun mapCategory(categories: List<ApiVenueCategory>?): String? {
        if (categories == null || categories.isEmpty()) {
            return null
        }

        return categories.firstOrNull {
            it.primary == true
        }?.name
    }

    private fun mapVenue(apiVenue: ApiVenue): Venue? {
        return Venue(
            id = apiVenue.id ?: return null,
            name = apiVenue.name ?: return null,
            venueLocation = mapLocation(apiVenue.location) ?: return null,
            category = mapCategory(apiVenue.categories)
        )
    }

    private fun mapLocation(apiLocation: ApiLocation?): VenueLocation? {
        return VenueLocation(
            Location(
                lat = apiLocation?.lat ?: return null,
                lon = apiLocation.lon ?: return null

            ),
            address = apiLocation.address
        )
    }

    private fun mapCategory(venueCategory: VenueCategory): String {
        return when (venueCategory) {
            VenueCategory.FOOD -> CATEGORY_FOOD
        }
    }

}
