package org.grigoryfedorov.restaurantsmap.interactor

import kotlinx.coroutines.flow.Flow
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue

interface MapInteractor {

    suspend fun getVenues(
        locationBox: LocationBox
    ): Flow<Set<Venue>>
}
