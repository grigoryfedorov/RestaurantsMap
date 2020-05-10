package org.grigoryfedorov.restaurantsmap.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.grigoryfedorov.restaurantsmap.R
import org.grigoryfedorov.restaurantsmap.di.MainModule
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.util.permission.Permission
import org.grigoryfedorov.restaurantsmap.util.permission.PermissionRequester


class MapFragment(private val mainModule: MainModule)
    : Fragment(R.layout.fragment_map) {

    companion object {
        private const val DEFAULT_ZOOM: Float = 11f
    }

    private var mMap: GoogleMap? = null

    private lateinit var viewModel: MapViewModel
    private lateinit var permissionRequester: PermissionRequester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionRequester = createPermissionRequester()
        viewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObservers()
        initMap()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }

    private fun createPermissionRequester(): PermissionRequester {
        return PermissionRequester(
            this,
            mainModule.permissionMapper
        )
    }

    private fun getViewModel(): MapViewModel {
        return ViewModelProvider(
            this,
            MapViewModelFactory(mainModule)
        )
            .get(MapViewModel::class.java)
    }

    private fun initViewModelObservers() {
        viewModel.permissionRequest.observe(viewLifecycleOwner, Observer {
            requestPermissions(it)
        })

        viewModel.venues.observe(viewLifecycleOwner, Observer {
            showVenues(it)
        })

        viewModel.mapState.observe(viewLifecycleOwner, Observer {
            processMapState(it)
        })

        viewModel.cameraAction.observe(viewLifecycleOwner, Observer {
            processCameraAction(it)
        })
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(::onMapReady)
    }

    private fun processMapState(it: MapState) {
        mMap?.isMyLocationEnabled = it.currentLocationEnabled
        mMap?.uiSettings?.isMyLocationButtonEnabled = it.currentLocationButtonVisible
    }

    private fun requestPermissions(it: List<Permission>) {
        permissionRequester.requestPermissions(it) {
            viewModel.onPermissionResult()
        }
    }

    private fun showVenues(it: List<Venue>) {
        for (venue in it) {
            mMap?.addMarker(
                MarkerOptions()
                    .position(mapLocationToLatLng(venue.location))
                    .title(venue.name)
            )
        }
    }

    private fun processCameraAction(it: CameraAction) {
        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapLocationToLatLng(it.location),
                DEFAULT_ZOOM
            )
        )
    }

    private fun mapLocationToLatLng(location: Location): LatLng {
        return LatLng(
            location.lat,
            location.lon
        )
    }

    private fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap == null) {
            return
        }

        mMap = googleMap
        viewModel.onMapReady()

        mMap?.setOnCameraIdleListener {
            mMap?.cameraPosition?.target?.let {
                viewModel.onCameraIdle(
                    Location(
                        lat = it.latitude,
                        lon = it.longitude
                    )
                )
            }
        }
    }

}