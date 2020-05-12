package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.data.venue.VenuesRepository
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory

class MapInteractorImpl(
    private val venuesRepository: VenuesRepository
) : MapInteractor {

    override suspend fun getVenues(
        locationBox: LocationBox
    ): List<Venue> {
        return venuesRepository.search(
            locationBox = locationBox,
            category = VenueCategory.FOOD
        )
    }
}
