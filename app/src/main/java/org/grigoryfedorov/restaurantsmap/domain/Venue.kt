package org.grigoryfedorov.restaurantsmap.domain

data class Venue(
    val id: String,
    val name: String,
    val location: VenueLocation,
    val category: String?
)
