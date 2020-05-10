package org.grigoryfedorov.restaurantsmap.di

import org.grigoryfedorov.restaurantsmap.interactor.DetailsInteractor
import org.grigoryfedorov.restaurantsmap.interactor.DetailsInteractorImpl

class DetailsModule(private val mainModule: MainModule) {
    val detailsInteractor: DetailsInteractor
        get() = createMapInteractor()

    private fun createMapInteractor(): DetailsInteractor {
        return DetailsInteractorImpl(mainModule.venuesRepository)
    }
}