package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue

interface MapInteractor {

    suspend fun getVenues(
        location: Location,
        radius: Int,
        limit: Int
    ): List<Venue>
}