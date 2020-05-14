package org.grigoryfedorov.restaurantsmap.domain

data class Venue(
    val id: String,
    val name: String,
    val venueLocation: VenueLocation,
    val category: String?
) {
    override fun equals(other: Any?): Boolean {
        return other is Venue && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
