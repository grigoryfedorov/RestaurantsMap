package org.grigoryfedorov.restaurantsmap.domain

data class VenueDetails(
    val id: String,
    val name: String,
    val location: Location,
    val rating: Double?,
    val category: String?,
    val hoursStatus: String?,
    val bestPhoto: String?

)