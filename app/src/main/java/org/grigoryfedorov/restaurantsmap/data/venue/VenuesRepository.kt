package org.grigoryfedorov.restaurantsmap.data.venue

import kotlinx.coroutines.flow.Flow
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

interface VenuesRepository {
    suspend fun search(
        locationBox: LocationBox,
        category: VenueCategory
    ): List<Venue>
    suspend fun getDetails(venueId: String): Flow<VenueDetails>
}
