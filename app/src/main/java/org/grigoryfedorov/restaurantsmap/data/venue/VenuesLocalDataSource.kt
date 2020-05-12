package org.grigoryfedorov.restaurantsmap.data.venue

import android.util.Log
import org.grigoryfedorov.restaurantsmap.data.venue.VenuesRepositoryImpl.Companion.TAG
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails

class VenuesLocalDataSource {

    private val storedVenues: MutableMap<String, Venue> = HashMap()
    private val venuesIdByLocation: MutableMap<Location, Set<String>> = HashMap()

    private val venueDetailsById: MutableMap<String, VenueDetails> = HashMap()

    fun search(
        location: Location,
        radius: Int
    ): List<Venue> {

        // if we need to filter by category also we probably need database (may be runtime)
        // or another map with category to id to intersect the results

        Log.i(TAG, "search local $location $radius")

        val ids = venuesIdByLocation.getOrElse(location, {emptySet()})


        val foundVenues = ids.mapNotNull {
            storedVenues[it]
        }

        Log.i(TAG, "local got ${foundVenues.size} $foundVenues")

        return foundVenues
    }

    fun putVenues(location: Location, newVenues: List<Venue>) {
        Log.i(TAG, "putVenues $location ${newVenues.size} $newVenues")

        val newVenuesMap = newVenues.associateBy { it.id }

        storedVenues.putAll(newVenuesMap)


        venuesIdByLocation[location] = newVenuesMap.keys.plus(venuesIdByLocation[location] ?: emptySet())


        Log.i(TAG, "putVenues cache all ${newVenues.size} $newVenues")
        Log.i(TAG, "putVenues cache by loc " +
                "${venuesIdByLocation.keys.size} " +
                "${venuesIdByLocation.values.size} " +
                "$venuesIdByLocation")
    }

    fun getDetails(venueId: String): VenueDetails? {
        return venueDetailsById[venueId]
    }

    fun putDetails(venueDetails: VenueDetails) {
        venueDetailsById[venueDetails.venue.id] = venueDetails

    }
}
