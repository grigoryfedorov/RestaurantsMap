package org.grigoryfedorov.restaurantsmap.ui.map

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.grigoryfedorov.restaurantsmap.R
import org.grigoryfedorov.restaurantsmap.di.MainModule
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.ui.navigation.NavigationViewModel
import org.grigoryfedorov.restaurantsmap.util.permission.Permission
import org.grigoryfedorov.restaurantsmap.util.permission.PermissionRequester


class MapFragment(private val mainModule: MainModule)
    : Fragment(R.layout.map_fragment) {

    private var map: GoogleMap? = null

    private lateinit var viewModel: MapViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var permissionRequester: PermissionRequester
    private var markerIcon: BitmapDescriptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionRequester = createPermissionRequester()
        viewModel = getViewModel()
        navigationViewModel = getNavigationViewModel()
        viewModel.onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObservers()
        loadMap()
        markerIcon = bitmapDescriptorFromVector(requireContext(), R.drawable.ic_pin)
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

    private fun getNavigationViewModel(): NavigationViewModel {
        return ViewModelProvider(requireActivity()).get(NavigationViewModel::class.java)
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

        viewModel.userAction.observe(viewLifecycleOwner, Observer {
            processUserAction(it)
        })
    }

    private fun loadMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(::onMapReady)
    }

    private fun processMapState(it: MapState) {
        map?.isMyLocationEnabled = it.currentLocationEnabled
        map?.uiSettings?.isMyLocationButtonEnabled = true
    }

    private fun requestPermissions(it: List<Permission>) {
        permissionRequester.requestPermissions(it) {
            viewModel.onPermissionResult()
        }
    }

    private fun showVenues(venues: List<Venue>) {
        for (venue in venues) {
            val marker = map?.addMarker(
                MarkerOptions()
                    .icon(markerIcon)
                    .position(mapLocationToLatLng(venue.venueLocation.location))
                    .title(venue.name)
                    .snippet(venue.category)
            )
            marker?.tag = venue.id
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun processCameraAction(it: CameraAction) {
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapLocationToLatLng(it.location),
                it.zoom
            )
        )
    }

    private fun processUserAction(it: UserAction) {
        when(it) {
            UserAction.LocationEnablePrompt -> {
                showLocationEnableDialog()
            }
       }
    }

    private fun showLocationEnableDialog() {
        AlertDialog.Builder(context)
            .setTitle(R.string.location_disabled_title)
            .setMessage(R.string.location_disabled_message)
            .setPositiveButton(R.string.location_disabled_positive_button
            ) { _, _ ->
                requireActivity().startActivity(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                )
            }
            .setNegativeButton(R.string.location_disabled_negative_button, null)
            .show()
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

        initMap(googleMap)
    }

    private fun initMap(googleMap: GoogleMap) {
        map = googleMap

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));
        googleMap.setOnCameraIdleListener {
            val center = googleMap.cameraPosition.target
            val zoom = googleMap.cameraPosition.zoom
            googleMap.projection.visibleRegion.latLngBounds.let {
                viewModel.onCameraIdle(
                    locationBox = LocationBox(
                        northEast = mapLocation(it.northeast),
                        southWest = mapLocation(it.southwest)
                    ),
                    cameraCenter = mapLocation(center),
                    zoom = zoom
                )
            }
        }

        googleMap.setOnCameraMoveStartedListener {
            viewModel.onCameraMoveStarted()
        }

        googleMap.setOnInfoWindowClickListener {
            val id: String? = it?.tag as? String
            if (id != null) {
                navigationViewModel.details(id)
            }
        }

        googleMap.setOnMyLocationButtonClickListener {
            viewModel.onMyLocationButtonClick()
            false
        }

        viewModel.onMapReady()
    }

    private fun mapLocation(latLng: LatLng): Location {
        return Location(
            lat = latLng.latitude,
            lon = latLng.longitude
        )
    }

}
