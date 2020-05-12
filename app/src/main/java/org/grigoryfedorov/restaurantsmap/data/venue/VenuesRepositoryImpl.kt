package org.grigoryfedorov.restaurantsmap.data.venue

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

class VenuesRepositoryImpl(
    private val venuesDataSource: VenuesDataSource,
    private val venuesLocalDataSource: VenuesLocalDataSource
) : VenuesRepository {

    companion object {
        const val TAG = "VenuesRepository"
    }

    override suspend fun search(
        locationBox: LocationBox,
        category: VenueCategory
    ): List<Venue> {
        Log.i(TAG, "search $locationBox")

        val remoteResults = venuesDataSource.search(
            locationBox = locationBox,
            category = category
        )

        Log.i(TAG, "search remote results ${remoteResults.size} + $remoteResults")

        return remoteResults
    }

    override suspend fun getDetails(venueId: String): Flow<VenueDetails> {
        return flow<VenueDetails> {
            // we can change cache policy here: we can call remote data source even if we have cache
            val localDetails = venuesLocalDataSource.getDetails(venueId)
            if (localDetails != null) {
                Log.i(TAG, "getDetails got local details! $venueId $localDetails")
                emit(localDetails)
            } else {
                val remoteDetails = venuesDataSource.getDetails(venueId)
                venuesLocalDataSource.putDetails(remoteDetails)
                emit(remoteDetails)
            }
        }

    }
}
