package org.grigoryfedorov.restaurantsmap.di

import android.content.Context
import okhttp3.logging.HttpLoggingInterceptor
import org.grigoryfedorov.restaurantsmap.util.permission.PermissionChecker
import org.grigoryfedorov.restaurantsmap.data.ApiVersionInterceptor
import org.grigoryfedorov.restaurantsmap.data.ClientKeyInterceptor
import org.grigoryfedorov.restaurantsmap.data.OkHttpClientFactory
import org.grigoryfedorov.restaurantsmap.data.host.FoursquareApiHostProvider
import org.grigoryfedorov.restaurantsmap.data.host.HostProvider
import org.grigoryfedorov.restaurantsmap.data.product.VenuesDataSource
import org.grigoryfedorov.restaurantsmap.data.product.VenuesRepository
import org.grigoryfedorov.restaurantsmap.data.product.VenuesRepositoryImpl
import org.grigoryfedorov.restaurantsmap.data.product.VenuesService
import org.grigoryfedorov.restaurantsmap.util.location.LocationManager
import org.grigoryfedorov.restaurantsmap.util.permission.PermissionMapper
import org.grigoryfedorov.restaurantsmap.util.resource.ResourceManager
import org.grigoryfedorov.restaurantsmap.util.resource.ResourceManagerImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainModule(context: Context) {
    val venuesRepository: VenuesRepository
    val resourceManager: ResourceManager
    val permissionChecker: PermissionChecker
    val permissionMapper: PermissionMapper
    val locationManager: LocationManager

    init {
        resourceManager = ResourceManagerImpl(context)
        venuesRepository = createVenuesRepository()
        permissionMapper = PermissionMapper()
        permissionChecker =
            PermissionChecker(
                context,
                permissionMapper
            )
        locationManager = LocationManager(context)


    }


    private fun createVenuesRepository(): VenuesRepository {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClientFactory()
            .create(
                ClientKeyInterceptor(resourceManager).interceptor,
                ApiVersionInterceptor(resourceManager).interceptor,
                loggingInterceptor
            )

        val hostProvider: HostProvider = FoursquareApiHostProvider(resourceManager)

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(hostProvider.host)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(VenuesService::class.java)


        val venuesDataSource = VenuesDataSource(service)

        return VenuesRepositoryImpl(venuesDataSource)
    }
}
