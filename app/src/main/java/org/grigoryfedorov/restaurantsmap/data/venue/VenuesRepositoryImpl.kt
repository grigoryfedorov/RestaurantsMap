package org.grigoryfedorov.restaurantsmap.data.venue

import kotlinx.coroutines.flow.flow
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueCategory
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

class VenuesRepositoryImpl(
    private val venuesRemoteRemoteDataSource: VenuesRemoteDataSource,
    private val venueSearchCache: VenueSearchCache,
    private val venuesDetailsCache: VenueDetailsCache
) : VenuesRepository {

    companion object {
        const val TAG = "VenuesRepository"
    }

    override suspend fun search(
        locationBox: LocationBox,
        category: VenueCategory
    ) = flow<Set<Venue>> {
        val localResultsSet = venueSearchCache.search(locationBox).toSet()

        emit(localResultsSet)

        val remoteResults = venuesRemoteRemoteDataSource.search(
            locationBox = locationBox,
            category = category
        )

        venueSearchCache.putVenues(remoteResults)

        emit(localResultsSet + remoteResults)
    }

    override suspend fun getDetails(venueId: String) = flow<VenueDetails> {
        // we can change cache policy here: we can call remote data source even if we have cache
        val localDetails = venuesDetailsCache.getDetails(venueId)
        if (localDetails != null) {
            emit(localDetails)
        } else {
            val remoteDetails = venuesRemoteRemoteDataSource.getDetails(venueId)
            venuesDetailsCache.putDetails(remoteDetails)
            emit(remoteDetails)
        }
    }
}
