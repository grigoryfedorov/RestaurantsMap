package org.grigoryfedorov.restaurantsmap.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.grigoryfedorov.restaurantsmap.di.MainModule
import org.grigoryfedorov.restaurantsmap.di.MapModule


class MapViewModelFactory(
    private val mainModule: MainModule
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            val mapViewModel: MapViewModel = createViewModel()

            @Suppress("UNCHECKED_CAST")
            return mapViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    private fun createViewModel(): MapViewModel {
        val module = MapModule(mainModule)

        return MapViewModel(
            module.mapInteractor,
            mainModule.permissionChecker,
            mainModule.locationManager
        )
    }
}
