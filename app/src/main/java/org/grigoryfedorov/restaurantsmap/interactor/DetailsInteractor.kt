package org.grigoryfedorov.restaurantsmap.interactor

import kotlinx.coroutines.flow.Flow
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

interface DetailsInteractor {
    suspend fun getDetails(venueId: String): Flow<VenueDetails>
}
