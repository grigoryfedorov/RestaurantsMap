package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

class VenueDetailsCache {
    private val venueDetailsById: MutableMap<String, VenueDetails> = HashMap()

    fun getDetails(venueId: String): VenueDetails? {
        return venueDetailsById[venueId]
    }

    fun putDetails(venueDetails: VenueDetails) {
        venueDetailsById[venueDetails.venue.id] = venueDetails
    }
}