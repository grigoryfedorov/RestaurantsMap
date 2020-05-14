package org.grigoryfedorov.restaurantsmap.interactor

import kotlinx.coroutines.flow.Flow
import org.grigoryfedorov.restaurantsmap.data.venue.VenuesRepository
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory

class MapInteractorImpl(
    private val venuesRepository: VenuesRepository
) : MapInteractor {

    override suspend fun getVenues(
        locationBox: LocationBox
    ): Flow<Set<Venue>> {
        return venuesRepository.search(
            locationBox = locationBox,
            category = VenueCategory.FOOD
        )
    }
}
