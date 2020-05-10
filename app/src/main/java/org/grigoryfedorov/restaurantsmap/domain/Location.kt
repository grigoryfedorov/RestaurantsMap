package org.grigoryfedorov.restaurantsmap.domain

data class Location(
    val lat: Double,
    val lon: Double,
    val address: String? = null
)