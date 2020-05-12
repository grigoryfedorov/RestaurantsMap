package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue

interface MapInteractor {

    suspend fun getVenues(
        locationBox: LocationBox
    ): List<Venue>
}
