package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

class VenuesRepositoryImpl(
    private val venuesDataSource: VenuesDataSource
) : VenuesRepository {

    override suspend fun search(
        location: Location,
        category: VenueCategory,
        limit: Int,
        radius: Int
    ): List<Venue> {
        return venuesDataSource.search(
            location = location,
            radius = radius,
            limit = limit,
            category = category
        )
    }

    override suspend fun getDetails(venueId: String): VenueDetails {
        return venuesDataSource.getDetails(venueId)
    }
}
