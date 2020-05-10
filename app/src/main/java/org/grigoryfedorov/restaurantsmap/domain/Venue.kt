package org.grigoryfedorov.restaurantsmap.domain

data class Venue(
    val id: String,
    val name: String,
    val location: Location,
    val category: String?
)