package org.grigoryfedorov.restaurantsmap.interactor

import org.grigoryfedorov.restaurantsmap.data.product.VenuesRepository
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue

class MapInteractorImpl(
    private val venuesRepository: VenuesRepository
) : MapInteractor {

    override suspend fun getRestaurants(location: Location): List<Venue> {
        return venuesRepository.search(location)
    }
}