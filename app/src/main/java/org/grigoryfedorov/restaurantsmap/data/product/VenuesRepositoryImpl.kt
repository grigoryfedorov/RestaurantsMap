package org.grigoryfedorov.restaurantsmap.data.product

import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue

class VenuesRepositoryImpl(
    private val venuesDataSource: VenuesDataSource
) : VenuesRepository {
    override suspend fun search(location: Location): List<Venue> {
        return venuesDataSource.search(location)
    }
}
