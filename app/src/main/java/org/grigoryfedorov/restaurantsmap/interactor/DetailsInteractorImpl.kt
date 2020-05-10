package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.data.venue.VenuesRepository
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

class DetailsInteractorImpl(
    private val venuesRepository: VenuesRepository
) : DetailsInteractor {
    override suspend fun getDetails(venueId: String): VenueDetails {
        return venuesRepository.getDetails(venueId)
    }
}