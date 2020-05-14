package org.grigoryfedorov.restaurantsmap.ui.details

import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

sealed class DetailsState {
    object Progress: DetailsState()

    data class Content(
        val venueDetails: VenueDetails
    ) : DetailsState()

    data class Error(
        val message: String
    ) : DetailsState()
}