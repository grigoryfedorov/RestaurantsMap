package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.data.venue.model.*
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails
import java.io.IOException

class VenuesDataSource(private val venuesService: VenuesService) {

    companion object {
        const val CATEGORY_FOOD = "4d4b7105d754a06374d81259"
    }

    suspend fun search(
        location: Location,
        radius: Int,
        limit: Int,
        category: VenueCategory
    ): List<Venue> {

        val ll = "${location.lat},${location.lon}"

        val apiVenuesResponse = venuesService.search(
            ll = ll,
            radius = radius,
            limit = limit,
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

    private fun mapVenueDetails(venueDetails: ApiVenueDetails?): VenueDetails? {
        return VenueDetails(
            id = venueDetails?.id ?: return null,
            name = venueDetails.name ?: return null,
            location = mapLocation(venueDetails.location) ?: return null,
            category = mapCategory(venueDetails.categories),
            rating = venueDetails.rating,
            hoursStatus = mapHours(venueDetails.hours),
            bestPhoto = mapBestPhoto(venueDetails.bestPhoto)
        )
    }

    private fun mapBestPhoto(bestPhoto: ApiVenuePhoto?): String? {
        val suffix: String = bestPhoto?.suffix ?: ""
        return bestPhoto?.prefix + suffix
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
            location = mapLocation(apiVenue.location) ?: return null,
            category = mapCategory(apiVenue.categories)
        )
    }

    private fun mapLocation(apiLocation: ApiLocation?): Location? {
        return Location(
            lat = apiLocation?.lat ?: return null,
            lon = apiLocation.lon ?: return null,
            address = apiLocation.address
        )
    }

    private fun mapCategory(venueCategory: VenueCategory): String {
        return when(venueCategory) {
            VenueCategory.FOOD -> CATEGORY_FOOD
        }

    }

}
