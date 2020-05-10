package org.grigoryfedorov.restaurantsmap.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.grigoryfedorov.restaurantsmap.di.DetailsModule
import org.grigoryfedorov.restaurantsmap.di.MainModule

class DetailsViewModelFactory(
    private val mainModule: MainModule,
    private val venueId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {

            val detailsViewModel: DetailsViewModel = createViewModel()

            @Suppress("UNCHECKED_CAST")
            return detailsViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    private fun createViewModel(): DetailsViewModel {
        val detailsModule = DetailsModule(mainModule)
        return DetailsViewModel(venueId, detailsModule.detailsInteractor)
    }
}