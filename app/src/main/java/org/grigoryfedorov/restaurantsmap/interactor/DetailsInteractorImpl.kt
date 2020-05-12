package org.grigoryfedorov.restaurantsmap.interactor

import kotlinx.coroutines.flow.Flow
import org.grigoryfedorov.restaurantsmap.data.venue.VenuesRepository
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

class DetailsInteractorImpl(
    private val venuesRepository: VenuesRepository
) : DetailsInteractor {
    override suspend fun getDetails(venueId: String): Flow<VenueDetails> {
        return venuesRepository.getDetails(venueId)
    }
}
