package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.data.venue.model.LocationKey
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue

class VenueSearchCache(private val locationKeyMapper: LocationKeyMapper) {

    private val venuesByLocation: MutableMap<LocationKey, HashSet<Venue>> = HashMap()

    fun search(
        locationBox: LocationBox
    ): List<Venue> {

        val northEastKey = locationKeyMapper.map(locationBox.northEast)
        val southWestKey = locationKeyMapper.map(locationBox.southWest)

        val result = ArrayList<Venue>()

        for (lat in southWestKey.lat..northEastKey.lat) {
            for (lon in southWestKey.lon..northEastKey.lon) {
                result.addAll(venuesByLocation[LocationKey(lat = lat, lon = lon)] ?: emptyList())
            }
        }

        return result.filter {
            isLocationInBox(it.venueLocation.location, locationBox)
        }
    }

    fun putVenues(newVenues: List<Venue>) {
        newVenues.forEach {
            val locationKey = locationKeyMapper.map(it.venueLocation.location)

            val set = venuesByLocation[locationKey]
            if (set == null) {
                venuesByLocation[locationKey] = hashSetOf(it)
            } else {
                set.add(it)
            }
        }
    }

    private fun isLocationInBox(
        location: Location,
        locationBox: LocationBox
    ): Boolean {
        return (location.lat >= locationBox.southWest.lat
                && location.lon >= locationBox.southWest.lon
                && location.lat <= locationBox.northEast.lat
                && location.lon <= locationBox.northEast.lon)
    }

}