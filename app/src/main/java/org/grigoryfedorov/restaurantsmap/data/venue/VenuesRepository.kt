package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

interface VenuesRepository {
    suspend fun search(
        location: Location,
        category: VenueCategory,
        limit: Int,
        radius: Int
    ): List<Venue>
    suspend fun getDetails(venueId: String): VenueDetails
}
