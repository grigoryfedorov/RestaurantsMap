package org.grigoryfedorov.restaurantsmap.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
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
        const val MIN_ZOOM_TO_SEARCH = 11F
    }

    val venues: LiveData<List<Venue>>
        get() = _venues
    val permissionRequest: LiveData<List<Permission>>
        get() = _permissionRequest
    val mapState: LiveData<MapState>
        get() = _mapState
    val cameraAction: LiveData<CameraAction>
        get() = _cameraAction
    val userAction: LiveData<UserAction>
        get() = _userAction

    private val _venues = SingleLiveEvent<List<Venue>>()
    private val _permissionRequest = SingleLiveEvent<List<Permission>>()
    private val _mapState = MutableLiveData<MapState>()
    private val _cameraAction = SingleLiveEvent<CameraAction>()
    private val _userAction = SingleLiveEvent<UserAction>()

    private var searchJob: Job? = null
    private var locationJob: Job? = null

    private var isCameraMoved = false

    private val shownVenues = HashSet<String>()

    fun onCreate() {
        shownVenues.clear()
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

    fun onMyLocationButtonClick() {
        if (!locationManager.isLocationEnabled()) {
            _userAction.value = UserAction.LocationEnablePrompt
        }
    }


    fun onCameraIdle(
        locationBox: LocationBox,
        zoom: Float
    ) {
        Log.i(TAG, "onCameraIdle new $locationBox zoom $zoom")

        if (zoom >= MIN_ZOOM_TO_SEARCH) {
            searchVenues(locationBox)
        }
    }

    private fun searchVenues(locationBox: LocationBox) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            kotlin.runCatching {
                mapInteractor.getVenues(locationBox)
                    .collect { newVenues ->
                        Log.i(TAG, "got venues ${newVenues.size} ")

                        val venuesToShow = newVenues.filter {
                            !shownVenues.contains(it.id)
                        }

                        val newVenuesIds = venuesToShow.map {
                            it.id
                        }

                        shownVenues.addAll(newVenuesIds)

                        _venues.value = venuesToShow
                    }
                Log.i(TAG, "finish search ")
            }.onFailure {
                Log.w(TAG, "Error getting rests ${it.message}", it)
            }
        }
    }

    fun onCameraMoveStarted() {
        isCameraMoved = true
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
                if (!isCameraMoved) {
                    _cameraAction.value = CameraAction(it)
                }
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
