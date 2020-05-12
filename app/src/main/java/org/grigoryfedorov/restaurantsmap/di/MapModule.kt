package org.grigoryfedorov.restaurantsmap.di

import org.grigoryfedorov.restaurantsmap.interactor.MapInteractor
import org.grigoryfedorov.restaurantsmap.interactor.MapInteractorImpl

class MapModule(private val mainModule: MainModule) {
    val mapInteractor: MapInteractor
        get() = createMapInteractor()

    private fun createMapInteractor(): MapInteractor {
        return MapInteractorImpl(mainModule.venuesRepository)
    }
}
