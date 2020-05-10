package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.data.venue.VenuesRepository
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory

class MapInteractorImpl(
    private val venuesRepository: VenuesRepository
) : MapInteractor {

    override suspend fun getVenues(
        location: Location,
        radius: Int,
        limit: Int
    ): List<Venue> {
        return venuesRepository.search(
            location,
            VenueCategory.FOOD,
            limit = limit,
            radius = radius
        )
    }
}