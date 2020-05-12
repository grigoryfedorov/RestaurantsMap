package org.grigoryfedorov.restaurantsmap.domain

data class VenueDetails(
    val venue: Venue,
    val rating: Double?,
    val hoursStatus: String?,
    val bestPhoto: String?
)
