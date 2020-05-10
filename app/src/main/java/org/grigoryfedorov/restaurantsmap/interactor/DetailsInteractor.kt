package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

interface DetailsInteractor {
    suspend fun getDetails(venueId: String): VenueDetails
}