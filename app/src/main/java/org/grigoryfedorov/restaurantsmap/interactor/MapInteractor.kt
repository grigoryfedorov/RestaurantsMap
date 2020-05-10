package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue

interface MapInteractor {

    suspend fun getRestaurants(location: Location): List<Venue>
}