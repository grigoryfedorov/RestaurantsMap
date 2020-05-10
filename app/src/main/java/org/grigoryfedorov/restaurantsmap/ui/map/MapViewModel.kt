package org.grigoryfedorov.restaurantsmap.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.interactor.MapInteractor
import org.grigoryfedorov.restaurantsmap.ui.SingleLiveEvent
import org.grigoryfedorov.restaurantsmap.util.location.LocationManager
import org.grigoryfedorov.restaurantsmap.util.permission.Permission
import org.grigoryfedorov.restaurantsmap.util.permission.PermissionChecker

class MapViewModel(
    private val mapInteractor: MapInteractor,
    private val permissionChecker: PermissionChecker,
    private val locationManager: LocationManager
) : ViewModel() {

    companion object {
        const val TAG = "MapViewModel"
        const val RADIUS_METERS = 1000
        const val VENUES_SEARCH_LIMIT = 50
    }

    val venues: LiveData<List<Venue>>
        get() = _venues
    val permissionRequest: LiveData<List<Permission>>
        get() = _permissionRequest
    val mapState: LiveData<MapState>
        get() = _mapState
    val cameraAction: LiveData<CameraAction>
        get() = _cameraAction

    private val _venues = SingleLiveEvent<List<Venue>>()
    private val _permissionRequest = SingleLiveEvent<List<Permission>>()
    private val _mapState = MutableLiveData<MapState>()
    private val _cameraAction = SingleLiveEvent<CameraAction>()

    private var searchJob: Job? = null
    private var locationJob: Job? = null

    fun onStart() {
    }

    fun onMapReady() {
        val hasLocationPermission = hasLocationPermission()

        setCurrentLocationEnabled(hasLocationPermission)
        if (hasLocationPermission) {
            updateCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    fun onCameraIdle(location: Location) {
        Log.i(TAG, "onCameraIdle loc $location")
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            kotlin.runCatching {
                mapInteractor.getVenues(location, RADIUS_METERS, VENUES_SEARCH_LIMIT)
            }.onSuccess { venues ->
                Log.i(TAG, "got rests $venues")
                _venues.value = venues
            }.onFailure {
                Log.w(TAG, "Error getting rests ${it.message}", it)
            }
        }
    }

    fun onPermissionResult() {
        val hasLocationPermission = hasLocationPermission()
        setCurrentLocationEnabled(hasLocationPermission())
        if (hasLocationPermission) {
            updateCurrentLocation()
        }
    }


    fun onStop() {
        searchJob?.cancel()
    }

    private fun hasLocationPermission(): Boolean {
        return permissionChecker.hasPermission(Permission.LOCATION)
    }

    private fun requestLocationPermission() {
        _permissionRequest.value = listOf(Permission.LOCATION)
    }

    private fun updateCurrentLocation() {
        locationJob?.cancel()
        locationJob = viewModelScope.launch {
            runCatching {
                locationManager.requestLocation()
            }.onSuccess {
                _cameraAction.value = CameraAction(it)
            }.onFailure {
                Log.w(TAG, "Could not get current location ${it.message}", it)
            }
        }
    }

    private fun setCurrentLocationEnabled(enabled: Boolean) {
        _mapState.value = MapState(
            currentLocationEnabled = enabled,
            currentLocationButtonVisible = enabled
        )
    }
}
