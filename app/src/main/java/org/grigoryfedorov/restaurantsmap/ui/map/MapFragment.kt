package org.grigoryfedorov.restaurantsmap.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
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
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.ui.navigation.NavigationViewModel
import org.grigoryfedorov.restaurantsmap.util.permission.Permission
import org.grigoryfedorov.restaurantsmap.util.permission.PermissionRequester


class MapFragment(private val mainModule: MainModule)
    : Fragment(R.layout.map_fragment) {

    companion object {
        private const val DEFAULT_ZOOM: Float = 14f
    }

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObservers()
        initMap()
        markerIcon = bitmapDescriptorFromVector(requireContext(), R.drawable.ic_place)
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
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(::onMapReady)
    }

    private fun processMapState(it: MapState) {
        map?.isMyLocationEnabled = it.currentLocationEnabled
        map?.uiSettings?.isMyLocationButtonEnabled = it.currentLocationButtonVisible
    }

    private fun requestPermissions(it: List<Permission>) {
        permissionRequester.requestPermissions(it) {
            viewModel.onPermissionResult()
        }
    }

    private fun showVenues(it: List<Venue>) {
        for (venue in it) {
            val marker = map?.addMarker(
                MarkerOptions()
                    .icon(markerIcon)
                    .position(mapLocationToLatLng(venue.location))
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

        map = googleMap
        viewModel.onMapReady()

        map?.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));
        map?.setOnCameraIdleListener {
            map?.cameraPosition?.target?.let {
                viewModel.onCameraIdle(
                    Location(
                        lat = it.latitude,
                        lon = it.longitude
                    )
                )
            }
        }

        map?.setOnCameraMoveStartedListener {
            viewModel.onCameraMoveStarted()
        }

        map?.setOnInfoWindowClickListener {
            val id: String? = it?.tag as? String
            if (id != null) {
                navigationViewModel.details(id)
            }
        }
    }

}