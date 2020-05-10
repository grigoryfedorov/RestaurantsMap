package org.grigoryfedorov.restaurantsmap.data.product

import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue

interface VenuesRepository {
    suspend fun search(location: Location): List<Venue>
}
